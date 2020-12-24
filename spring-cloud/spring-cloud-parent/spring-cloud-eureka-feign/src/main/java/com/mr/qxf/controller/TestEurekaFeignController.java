package com.mr.qxf.controller;

import com.mr.qxf.model.UserModel;
import com.mr.qxf.service.TestEurekaFeignServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(value="eureka-feign-controller")
public class TestEurekaFeignController {
    @Resource
    private TestEurekaFeignServiceI service;

    @GetMapping
    public String test(String name){
        return service.test(name);
    }
    @GetMapping(value="user")
    public String test2(UserModel user){
        return service.test2(user);
    }

    @PostMapping
    public String test3(UserModel user){
        return service.test3(user);
    }

    @PutMapping
    public String test4(UserModel user){
        return service.test4(user);
    }

    @DeleteMapping
    public String test5(UserModel user){
        return service.test5(user.getIds());
    }
}
