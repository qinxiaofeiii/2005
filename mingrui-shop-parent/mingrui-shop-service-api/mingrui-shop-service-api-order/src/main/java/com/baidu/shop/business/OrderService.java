package com.baidu.shop.business;

import com.alibaba.fastjson.JSONObject;
import com.baidu.shop.base.Result;
import com.baidu.shop.dto.AddressDto;
import com.baidu.shop.dto.OrderDTO;
import com.baidu.shop.dto.OrderInfo;
import com.baidu.shop.entity.AddressEntity;
import com.baidu.shop.entity.OrderEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "订单接口")
public interface OrderService {

    @ApiOperation(value = "创建订单")
    @PostMapping(value = "order/createOrder")
    Result<String> createOrder(@RequestBody OrderDTO orderDTO, @CookieValue(value = "MRSHOP_TOKEN") String token);

    @ApiOperation(value = "根据用户查询订单信息")
    @GetMapping(value = "order/getOrderInfoByUser")
    Result<List<OrderInfo>> getOrderInfo(@CookieValue(value = "MRSHOP_TOKEN") String token,Integer page,Integer rows);

    @ApiOperation(value = "根据订单id查询订单信息")
    @GetMapping(value = "order/getOrderInfoByOrderId")
    Result<OrderInfo> getOrderInfoByOrderId(@RequestParam Long orderId);

    @ApiOperation(value = "获取用户收货地址信息")
    @GetMapping(value = "order/getAddress")
    Result<List<AddressEntity>> getAddress(@CookieValue(value = "MRSHOP_TOKEN") String token);

    @ApiOperation(value = "新增收货地址")
    @PostMapping(value = "order/addAddress")
    Result<JSONObject> addAddress(@RequestBody AddressDto address, @CookieValue(value = "MRSHOP_TOKEN") String token);

    @ApiOperation(value = "删除收货地址")
    @GetMapping(value = "order/deleteAddress")
    Result<JSONObject> deleteAddress(@RequestParam Integer id);

    @ApiOperation(value = "修改收货地址")
    @PutMapping(value = "order/editAddress")
    Result<AddressEntity> editAddress(@RequestParam Integer id);
}
