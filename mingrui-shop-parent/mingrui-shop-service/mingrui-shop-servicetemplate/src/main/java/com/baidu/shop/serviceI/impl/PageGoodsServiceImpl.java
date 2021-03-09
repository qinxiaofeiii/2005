package com.baidu.shop.serviceI.impl;

import com.baidu.shop.base.Result;
import com.baidu.shop.dto.*;
import com.baidu.shop.entity.*;
import com.baidu.shop.feign.BrandFeign;
import com.baidu.shop.feign.CategoryFeign;
import com.baidu.shop.feign.GoodsFeign;
import com.baidu.shop.feign.SpecificationFeign;
import com.baidu.shop.serviceI.PageServiceI;
import com.baidu.shop.utils.BaiduBeanUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PageGoodsServiceImpl implements PageServiceI {
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


    @Override
    public Map<String, Object> getGoodsInfo(Integer spuId) {

            Map<String, Object> goodsInfoMap = new HashMap<>();

            //spu
            SpuDTO spuDTO = new SpuDTO();
            spuDTO.setId(spuId);
            Result<List<SpuDTO>> spuResult = goodsFeign.getSpuInfo(spuDTO);
            SpuDTO spuResultData = null;
            if(spuResult.isSuccess()){
                spuResultData = spuResult.getData().get(0);
                goodsInfoMap.put("spuInfo",spuResultData);
            }
            //spuDetail
            Result<SpuDetailEntity> spuDetailResult = goodsFeign.getSpuDetailBySpuId(spuId);
            if(spuDetailResult.isSuccess()){
                goodsInfoMap.put("spuDetail",spuDetailResult.getData());
            }
            //分类信息
            Result<List<CategoryEntity>> categoryResult = categoryFeign.getCateByIds(
                    String.join(
                            "," , Arrays.asList(spuResultData.getCid1() + "",
                                    spuResultData.getCid2() + "", spuResultData.getCid3() + "")
                    )
            );
            if(categoryResult.isSuccess()){
                goodsInfoMap.put("categoryInfo",categoryResult.getData());
            }
            //品牌信息
            BrandDTO brandDTO = new BrandDTO();
            brandDTO.setId(spuResultData.getBrandId());
            Result<PageInfo<BrandEntity>> brandResult = brandFeign.getBrandList(brandDTO);
            if(brandResult.isSuccess()){
                goodsInfoMap.put("brandInfo",brandResult.getData().getList().get(0));
            }
            //sku
            Result<List<SkuDTO>> skusResult = goodsFeign.getSkuAndStockBySpuId(spuId);
            if(skusResult.isSuccess()){
                goodsInfoMap.put("skus",skusResult.getData());
            }
            //规格组,规格参数(通用)
            SpecGroupDTO specGroupDTO = new SpecGroupDTO();
            specGroupDTO.setCid(spuResultData.getCid3());
            Result<List<SpecGroupEntity>> specGroupResult = specificationFeign.getSpecGroupInfo(specGroupDTO);
            if(specGroupResult.isSuccess()){

                List<SpecGroupEntity> specGroupList = specGroupResult.getData();
                List<SpecGroupDTO> specGroupAndParam = specGroupList.stream().map(specGroup -> {
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
                goodsInfoMap.put("specGroupAndParam",specGroupAndParam);
            }
            //特殊规格
            SpecParamDTO specParamDTO = new SpecParamDTO();
            specParamDTO.setCid(spuResultData.getCid3());
            specParamDTO.setGeneric(false);
            Result<List<SpecParamEntity>> specParamResult = specificationFeign.getSpecParamByGroupId(specParamDTO);
            if(specParamResult.isSuccess()){
                List<SpecParamEntity> specParamEntityList = specParamResult.getData();
                Map<Integer, String> specParamMap = new HashMap<>();
                specParamEntityList.stream().forEach(specParam -> specParamMap.put(specParam.getId(),specParam.getName()));
                goodsInfoMap.put("specParamMap",specParamMap);
            }

            return goodsInfoMap;
        }

    }
