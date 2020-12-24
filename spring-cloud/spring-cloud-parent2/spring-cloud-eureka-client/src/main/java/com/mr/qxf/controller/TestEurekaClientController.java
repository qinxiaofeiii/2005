package com.mr.qxf.controller;

import com.mr.qxf.model.UserModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="eureka-client-controller")
public class TestEurekaClientController {

    @GetMapping
    public String test(String name){
        System.err.println("==========string==============");
        return "eureka-client" + name;
    }

    @GetMapping(value="user")
    public String test2(UserModel user){
        System.err.println("============get=================");
        return "eureka-client" + user.getName();
    }

    @PostMapping
    public String test3(UserModel user){
        System.err.println("=============post=================");
        return "eureka-client" + user.getName();
    }

    @PutMapping
    public String test4(UserModel user){
        System.err.println("=============put=====================");
        return "eureka-client" + user.getName();
    }

    @DeleteMapping
    public String test5(UserModel user){
        System.err.println("===============delete=====================");
        return "eureka-client" + user.getIds();
    }
}
