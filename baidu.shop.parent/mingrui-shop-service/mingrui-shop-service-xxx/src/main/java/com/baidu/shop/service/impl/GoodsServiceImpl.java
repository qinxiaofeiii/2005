package com.baidu.shop.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baidu.shop.base.BaseApiService;
import com.baidu.shop.base.Result;
import com.baidu.shop.dto.SkuDTO;
import com.baidu.shop.dto.SpuDTO;
import com.baidu.shop.entity.*;
import com.baidu.shop.mapper.*;
import com.baidu.shop.service.GoodsServiceI;
import com.baidu.shop.status.HTTPStatus;
import com.baidu.shop.utils.BaiduBeanUtil;
import com.baidu.shop.utils.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class GoodsServiceImpl extends BaseApiService implements GoodsServiceI {

    @Resource
    private SpuMapper spuMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private BrandMapper brandMapper;

    @Resource
    private SkuMapper skuMapper;

    @Resource
    private SpuDetailMapper spuDetailMapper;

    @Resource
    private StockMapper stockMapper;

    @Override
    public Result<JSONObject> editSaleable(SpuEntity spuEntity) {

        spuMapper.updateByPrimaryKeySelective(spuEntity);

        return this.setResultSuccess();
    }

    @Override
    @Transactional
    public Result<JSONObject> deleteGoods(Integer spuId) {

        //删除spu
        spuMapper.deleteByPrimaryKey(spuId);
        //删除spuDetail
        spuDetailMapper.deleteByPrimaryKey(spuId);

        //根据spuId查询sku信息
        this.deleteSkuAndStock(spuId);

        return this.setResultSuccess();
    }

    @Override
    @Transactional
    public Result<JSONObject> editGoods(SpuDTO spuDTO) {

        final Date date = new Date();
        //修改spu
        SpuEntity spuEntity = BaiduBeanUtil.copyProperties(spuDTO, SpuEntity.class);
        spuEntity.setLastUpdateTime(date);
        spuMapper.updateByPrimaryKeySelective(spuEntity);

        //修改spuDetail
        spuDetailMapper.updateByPrimaryKeySelective(BaiduBeanUtil.copyProperties(spuDTO.getSpuDetail(),SpuDetailEntity.class));

        //删除sku和stock
        this.deleteSkuAndStock(spuEntity.getId());

        //新增sku表和stock表
        this.saveSkuAndStockInfo(spuDTO,spuEntity.getId(),date);

        return this.setResultSuccess();
    }

    @Override
    public Result<List<SkuDTO>> getSkuAndStockBySpuId(Integer spuId) {

        List<SkuDTO> list = skuMapper.getSkuAndStockBySpuId(spuId);

        return this.setResultSuccess(list);
    }

    @Override
    public Result<SpuDetailEntity> getSpuDetailBySpuId(Integer spuId) {

        SpuDetailEntity spuDetailEntity = spuDetailMapper.selectByPrimaryKey(spuId);

        return this.setResultSuccess(spuDetailEntity);
    }

    @Override
    @Transactional
    public Result<JSONObject> saveGoods(SpuDTO spuDTO) {

        final Date date = new Date();
        //新增商品spu
        SpuEntity spuEntity = BaiduBeanUtil.copyProperties(spuDTO, SpuEntity.class);
        spuEntity.setCreateTime(date);
        spuEntity.setLastUpdateTime(date);
        spuEntity.setSaleable(1);
        spuEntity.setValid(1);
        spuMapper.insertSelective(spuEntity);

        //新增spuDetail
        SpuDetailEntity spuDetailEntity = BaiduBeanUtil.copyProperties(spuDTO.getSpuDetail(), SpuDetailEntity.class);
        spuDetailEntity.setSpuId(spuEntity.getId());
        spuDetailMapper.insertSelective(spuDetailEntity);

        //新增sku和stock
        this.saveSkuAndStockInfo(spuDTO,spuEntity.getId(),date);

        return this.setResultSuccess();
    }

    @Override
    @Transactional
    public Result<List<SpuDTO>> getSpuInfo(SpuDTO spuDTO) {
        //分页
        if(ObjectUtil.isNotNull(spuDTO.getPage()) && ObjectUtil.isNotNull(spuDTO.getRows()))
            PageHelper.startPage(spuDTO.getPage(),spuDTO.getRows());

        //排序
        if(!StringUtils.isEmpty(spuDTO.getSort()) && !StringUtils.isEmpty(spuDTO.getOrder()))
            PageHelper.orderBy(spuDTO.getOrderBy());

        Example example = new Example(SpuEntity.class);
        Example.Criteria criteria = example.createCriteria();

        if(ObjectUtil.isNotNull(spuDTO.getSaleable()) && spuDTO.getSaleable() < 2)
            criteria.andEqualTo("saleable", spuDTO.getSaleable());
        if(!StringUtils.isEmpty(spuDTO.getTitle()))
            criteria.andLike("title","%"+spuDTO.getTitle()+"%");

        List<SpuEntity> spuEntities = spuMapper.selectByExample(example);

        List<SpuDTO> spuDTOList = spuEntities.stream().map(spuEntity -> {
            //返回类型是SpuDTO 所以讲spuEntity copy 到 SpuDTO
            SpuDTO spuDTO1 = BaiduBeanUtil.copyProperties(spuEntity, SpuDTO.class);

            //通过商品分类id集合查询数据
            List<CategoryEntity> categoryEntities = categoryMapper.selectByIdList(Arrays.asList(spuEntity.getCid1(),
                    spuEntity.getCid2(), spuEntity.getCid3()));

            //遍历categoryEntities集合将分类名称用 / 拼接
            String categoryName = categoryEntities.stream().map(categoryEntity ->
                    categoryEntity.getName()).collect(Collectors.joining("/"));
            spuDTO1.setCategoryName(categoryName);

            // 根据brandId查询数据
            BrandEntity brandEntity = brandMapper.selectByPrimaryKey(spuEntity.getBrandId());
            spuDTO1.setBrandName(brandEntity.getName());

            return spuDTO1;
        }).collect(Collectors.toList());

        PageInfo<SpuEntity> pageInfo = new PageInfo<>(spuEntities);

        return this.setResult(HTTPStatus.OK,pageInfo.getTotal()+ "",spuDTOList);
    }

    private void saveSkuAndStockInfo(SpuDTO spuDTO,Integer spuId,Date date){
        spuDTO.getSkus().stream().forEach(skuDTO -> {
            SkuEntity skuEntity = BaiduBeanUtil.copyProperties(skuDTO, SkuEntity.class);
            skuEntity.setSpuId(spuId);
            skuEntity.setCreateTime(date);
            skuEntity.setLastUpdateTime(date);
            skuMapper.insertSelective(skuEntity);

            //新增stock
            StockEntity stockEntity = new StockEntity();
            stockEntity.setSkuId(skuEntity.getId());
            stockEntity.setStock(skuDTO.getStock());
            stockMapper.insertSelective(stockEntity);
        });
    }

    private void deleteSkuAndStock(Integer spuId){
        Example example = new Example(SkuEntity.class);
        example.createCriteria().andEqualTo("spuId",spuId);
        List<SkuEntity> skuEntityList = skuMapper.selectByExample(example);
        List<Long> skuIdList = skuEntityList.stream().map(sku ->sku.getId()).collect(Collectors.toList());

        //根据skuId集合批量删除sku
        skuMapper.deleteByIdList(skuIdList);
        //根据skuId集合批量删除stock
        stockMapper.deleteByIdList(skuIdList);
    }
}
