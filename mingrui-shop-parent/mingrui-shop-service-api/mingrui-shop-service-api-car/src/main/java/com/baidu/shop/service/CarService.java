package com.baidu.shop.service;

import com.baidu.shop.base.Result;
import com.baidu.shop.dto.Car;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "购物车接口")
public interface CarService {

    @ApiOperation(value = "添加商品到购物车")
    @PostMapping(value = "car/addCar")
    Result<Car> addCar(@RequestBody Car car, @CookieValue(value = "MRSHOP_TOKEN") String token);

    @ApiOperation(value = "合并购物车")
    @PostMapping(value = "car/mergeCar")
    Result<JSONObject> mergeCar(@RequestBody String carList,@CookieValue("MRSHOP_TOKEN") String token);

    @ApiOperation(value = "获取当前登录用户的购物车信息")
    @GetMapping(value = "car/getUserCar")
    Result<List<Car>> getUserCar(@CookieValue(value = "MRSHOP_TOKEN") String token);

    @ApiOperation(value = "修改购物车商品的数量")
    @GetMapping(value = "car/operationGoods")
    Result<JSONObject> operationGoods(@CookieValue(value = "MRSHOP_TOKEN") String token,Long skuId,Integer type);

}
