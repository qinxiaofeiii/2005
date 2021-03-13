package com.baidu.shop.service;

import com.alibaba.fastjson.JSONObject;
import com.baidu.shop.base.Result;
import com.baidu.shop.dto.UserDTO;
import com.baidu.shop.entity.UserEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "用户接口")
public interface UserService {

    @ApiOperation(value = "校验手机验证码")
    @GetMapping(value = "user/checkCode")
    Result<JSONObject> checkCode(@RequestParam String phone,@RequestParam String code);

    @ApiOperation(value = "用户注册")
    @PostMapping(value = "user/register")
    Result<JSONObject> register(@RequestBody UserDTO userDTO);

    @ApiOperation(value = "校验用户名或手机号唯一")
    @GetMapping(value = "user/check")
    Result<List<UserEntity>> checkUserNameOrPhone(@RequestParam String value,@RequestParam Integer type);

    @ApiOperation(value = "给手机发送验证码")
    @PostMapping(value = "user/send")
    Result<JSONObject> send(@RequestBody UserDTO userDTO);

}
