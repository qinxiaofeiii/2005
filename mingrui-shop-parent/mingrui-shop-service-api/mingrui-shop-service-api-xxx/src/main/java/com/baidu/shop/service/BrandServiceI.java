package com.baidu.shop.service;

import com.alibaba.fastjson.JSONObject;
import com.baidu.shop.base.Result;
import com.baidu.shop.dto.BrandDTO;
import com.baidu.shop.entity.BrandEntity;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(tags = "商品品牌接口")
public interface BrandServiceI{
    @ApiOperation(value="查询商品品牌")
    @GetMapping(value = "/brand/list")
    Result<PageInfo<BrandEntity>> getBrandList(BrandDTO brandDTO);

    @ApiOperation(value="新增商品品牌")
    @PostMapping(value = "/brand/save")
    Result<JSONObject> saveBrand(@RequestBody BrandDTO brandDTO);

    @ApiOperation(value="修改商品品牌")
    @PutMapping(value = "/brand/save")
    Result<JSONObject> editBrand(@RequestBody BrandDTO brandDTO);

    @ApiOperation(value="删除商品品牌")
    @DeleteMapping(value = "/brand/delete")
    Result<JSONObject> deleteBrand(Integer id);

    @ApiOperation(value="根据分类id查询品牌信息")
    @GetMapping(value = "/brand/getBrandByCategoryId")
    Result<List<BrandEntity>> getBrandByCategoryId(Integer cid);
}
