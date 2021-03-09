package com.baidu.shop.serviceI.impl;

import com.alibaba.fastjson.JSONObject;
import com.baidu.shop.base.BaseApiService;
import com.baidu.shop.base.Result;
import com.baidu.shop.dto.*;
import com.baidu.shop.entity.*;
import com.baidu.shop.feign.BrandFeign;
import com.baidu.shop.feign.CategoryFeign;
import com.baidu.shop.feign.GoodsFeign;
import com.baidu.shop.feign.SpecificationFeign;
import com.baidu.shop.service.TemplateServiceI;
import com.baidu.shop.utils.BaiduBeanUtil;
import com.baidu.shop.utils.ObjectUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class TemplateServiceImpl extends BaseApiService implements TemplateServiceI {

    private final Integer CREATE_STATIC_HTML = 1;

    private final Integer DELETE_STATIC_HTML = 2;

    @Autowired
    private GoodsFeign goodsFeign;

    @Autowired
    private SpecificationFeign specificationFeign;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private BrandFeign brandFeign;

    @Autowired
    private CategoryFeign categoryFeign;

    @Value(value="${mrshop.static.html.path}")
    private String htmlPath;

    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public Result<JSONObject> createStaticHTMLTemplate(Integer spuId) {

        //获取需要渲染的数据
        Map<String, Object> goodsInfo = this.getGoodsInfo(spuId);

        Context context = new Context();
        context.setVariables(goodsInfo);
        File file = new File(htmlPath,spuId + ".html");
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(file,"UTF-8");
            templateEngine.process("item",context,printWriter);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            if(ObjectUtil.isNotNull(printWriter)){
                printWriter.close();
            }
        }

        return this.setResultSuccess();
    }

    @Override
    public Result<JSONObject> initStaticHTMLTemplate() {

        this.operationStaticHTML(CREATE_STATIC_HTML);

        return this.setResultSuccess();
    }

    @Override
    public Result<JSONObject> clearStaticHTMLTemplate() {

        this.operationStaticHTML(DELETE_STATIC_HTML);

        return this.setResultSuccess();
    }

    @Override
    public Result<JSONObject> deleteStaticHTMLTemplate(Integer spuId) {

        File file = new File(htmlPath, spuId + ".html");
        if (file.exists()){
            file.delete();
        }
        return this.setResultSuccess();
    }

    private Boolean operationStaticHTML(Integer operation){

        try {
            Result<List<SpuDTO>> spuInfo = goodsFeign.getSpuInfo(new SpuDTO());
            if(spuInfo.isSuccess()){
                spuInfo.getData().stream().forEach(spuDTO -> {
                    if(operation == 1){
                        this.createStaticHTMLTemplate(spuDTO.getId());
                    }else{
                        this.deleteStaticHTMLTemplate(spuDTO.getId());
                    }
                });
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    private Map<String, Object> getGoodsInfo(Integer spuId) {

        Map<String, Object> goodsInfoMap = new HashMap<>();
        //spu
        SpuDTO spuResultData = this.getSpuInfo(spuId);
        goodsInfoMap.put("spuInfo",spuResultData);
        //spuDetail
        goodsInfoMap.put("spuDetail",this.getSpuDetail(spuId));
        //分类信息
        goodsInfoMap.put("categoryInfo",this.getCategoryInfo(spuResultData.getCid1() + "",
                spuResultData.getCid2() + "",spuResultData.getCid3() + ""));
        //品牌信息
        goodsInfoMap.put("brandInfo",this.getBrandInfo(spuResultData.getBrandId()));
        //sku
        goodsInfoMap.put("skus",this.getSkus(spuId));
        //规格组,规格参数(通用)
        goodsInfoMap.put("specGroupAndParam",this.getSpecGroupAndParam(spuResultData.getCid3()));
        //特殊规格
        goodsInfoMap.put("specParamMap",this.getSpecParamMap(spuResultData.getCid3()));

        return goodsInfoMap;
    }

    private SpuDTO getSpuInfo(Integer spuId){
        SpuDTO spuDTO = new SpuDTO();
        spuDTO.setId(spuId);
        Result<List<SpuDTO>> spuResult = goodsFeign.getSpuInfo(spuDTO);
        if(!spuResult.isSuccess()) return null;

        return spuResult.getData().get(0);
    }

    //获取spuDetail信息
    private SpuDetailEntity getSpuDetail(Integer spuId){

        Result<SpuDetailEntity> spuDetailResult = goodsFeign.getSpuDetailBySpuId(spuId);
        if(!spuDetailResult.isSuccess())    return null;

        return spuDetailResult.getData();
    }

    //获取分类信息
    private List<CategoryEntity> getCategoryInfo(String cid1,String cid2,String cid3){

        Result<List<CategoryEntity>> categoryResult = categoryFeign.getCateByIds(
                String.join(
                        "," , Arrays.asList(cid1,cid2, cid3)
                )
        );
        if(!categoryResult.isSuccess())     return null;

        return categoryResult.getData();
    }

    //获取品牌信息
    private BrandEntity getBrandInfo(Integer brandId){

        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setId(brandId);
        Result<PageInfo<BrandEntity>> brandResult = brandFeign.getBrandList(brandDTO);
        if(!brandResult.isSuccess())    return null;

        return brandResult.getData().getList().get(0);
    }

    //获取sku信息
    private List<SkuDTO> getSkus(Integer spuId){

        Result<List<SkuDTO>> skusResult = goodsFeign.getSkuAndStockBySpuId(spuId);
        if(!skusResult.isSuccess())     return null;

        return skusResult.getData();
    }

    //获取规格组和特有规格参数List
    private List<SpecGroupDTO> getSpecGroupAndParam(Integer cid3){

        SpecGroupDTO specGroupDTO = new SpecGroupDTO();
        specGroupDTO.setCid(cid3);
        List<SpecGroupDTO> specGroupAndParam = null;

        Result<List<SpecGroupEntity>> specGroupResult = specificationFeign.getSpecGroupInfo(specGroupDTO);
        if(specGroupResult.isSuccess()){

            List<SpecGroupEntity> specGroupList = specGroupResult.getData();
            specGroupAndParam = specGroupList.stream().map(specGroup -> {
                SpecGroupDTO specGroupDTO1 = BaiduBeanUtil.copyProperties(specGroup, SpecGroupDTO.class);

                SpecParamDTO specParamDTO = new SpecParamDTO();
                specParamDTO.setGroupId(specGroupDTO1.getId());
                specParamDTO.setGeneric(true);
                Result<List<SpecParamEntity>> specParamResult = specificationFeign.getSpecParamByGroupId(specParamDTO);
                if (specParamResult.isSuccess()) {
                    specGroupDTO1.setSpecParamList(specParamResult.getData());
                }
                return specGroupDTO1;
            }).collect(Collectors.toList());

        }
        return specGroupAndParam;
    }

    //获取通用参数paramMap
    private Map<Integer, String> getSpecParamMap(Integer cid3){

        SpecParamDTO specParamDTO = new SpecParamDTO();
        Map<Integer, String> specParamMap = new HashMap<>();
        specParamDTO.setCid(cid3);
        specParamDTO.setGeneric(false);

        Result<List<SpecParamEntity>> specParamResult = specificationFeign.getSpecParamByGroupId(specParamDTO);
        if(specParamResult.isSuccess()){
            List<SpecParamEntity> specParamEntityList = specParamResult.getData();

            specParamEntityList.stream().forEach(specParam -> specParamMap.put(specParam.getId(),specParam.getName()));
        }
        return specParamMap;
    }

}
