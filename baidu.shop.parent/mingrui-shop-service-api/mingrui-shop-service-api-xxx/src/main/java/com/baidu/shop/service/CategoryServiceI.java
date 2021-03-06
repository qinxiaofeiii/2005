package com.baidu.shop.service;

import com.baidu.shop.base.Result;
import com.baidu.shop.entity.CategoryEntity;
import com.google.gson.JsonObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api (tags = "商品分类接口")
public interface CategoryServiceI {

    @ApiOperation(value="通过pid查询商品分类")
    @GetMapping(value = "category/list")
    Result<List<CategoryEntity>> getCategoryListByPid(Integer pid);

    @ApiOperation(value = "删除商品分类")
    @DeleteMapping(value = "/category/delete")
    Result<JsonObject> deleteCategoryById(Integer id);

    @ApiOperation(value = "通过id更新商品分类name")
    @PutMapping(value = "/category/update")
    Result<JsonObject> updateCategoryNameById(@RequestBody CategoryEntity categoryEntity);

    @ApiOperation(value = "新增商品分类")
    @PostMapping(value = "/category/save")
    Result<JsonObject> saveCategoryNameById(@RequestBody CategoryEntity categoryEntity);

    @ApiOperation(value = "通过brandId查询商品分类信息")
    @GetMapping(value = "category/brand")
    Result<List<CategoryEntity>> getCategoryByBrandId(Integer brandId);
}
