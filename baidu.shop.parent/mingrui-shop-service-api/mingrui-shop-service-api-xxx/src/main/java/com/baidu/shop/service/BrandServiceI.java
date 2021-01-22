package com.baidu.shop.service;

import com.baidu.shop.base.Result;
import com.baidu.shop.dto.BrandDTO;
import com.baidu.shop.entity.BrandEntity;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;

@Api(tags = "商品品牌接口")
public interface BrandServiceI{

    @ApiOperation(value="查询商品品牌")
    @GetMapping(value = "/brand/list")
    Result<PageInfo<BrandEntity>> getBrandList(BrandDTO brandDTO);
}
