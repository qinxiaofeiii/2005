package com.baidu.shop.service;

import com.alibaba.fastjson.JSONObject;
import com.baidu.shop.base.Result;
import com.baidu.shop.dto.SkuDTO;
import com.baidu.shop.dto.SpuDTO;
import com.baidu.shop.entity.SpuDetailEntity;
import com.baidu.shop.entity.SpuEntity;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "商品接口")
public interface GoodsServiceI {

    @ApiOperation(value = "获取spu信息")
    @GetMapping(value = "/goods/spuInfo")
    Result<List<SpuDTO>> getSpuInfo(SpuDTO spuDTO);

    @ApiOperation(value = "新增商品")
    @PostMapping(value = "/goods/save")
    Result<JSONObject>saveGoods(@RequestBody SpuDTO spuDTO);

    @ApiOperation(value = "修改商品")
    @PutMapping(value = "/goods/save")
    Result<JSONObject>editGoods(@RequestBody SpuDTO spuDTO);

    @ApiOperation(value = "删除商品")
    @DeleteMapping(value = "/goods/delete")
    Result<JSONObject>deleteGoods(Integer spuId);

    @ApiOperation(value = "根据spuId获取detail信息")
    @GetMapping(value = "/goods/getSpuDetailBySpuId")
    Result<SpuDetailEntity> getSpuDetailBySpuId(Integer spuId);

    @ApiOperation(value = "根据spuId获取sku信息")
    @GetMapping(value = "/goods/getSkuAndStockBySpuId")
    Result<List<SkuDTO>> getSkuAndStockBySpuId(Integer spuId);
}
