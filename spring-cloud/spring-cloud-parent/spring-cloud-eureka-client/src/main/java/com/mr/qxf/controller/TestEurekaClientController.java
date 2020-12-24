package com.mr.qxf.controller;

import com.mr.qxf.model.UserModel;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="eureka-client-controller")
@RefreshScope//动态刷新配置
public class TestEurekaClientController {
    @Value(value="${mingrui.url}")
    private String url;

    @GetMapping(value = "testUrl")
    public String getUrl(){
        return "从config-server读取内容--> " + url;
    }

    @GetMapping
    @HystrixCommand(fallbackMethod = "testFallbackMethod")
    public String test(String name){
        System.err.println("===========name============");
        return "eureka-client"+name;
    }

    @GetMapping(value="user")
    @HystrixCommand(fallbackMethod = "testFallbackMethod")
    public String test2(UserModel user){
        System.err.println("=========user==============");
        return "eureka-client"+user.getName();
    }
    @PostMapping
    @HystrixCommand(fallbackMethod = "testFallbackMethod")
    public String test3(UserModel user){
        System.err.println("=========userPost==============");
        return "eureka-client"+user.getName();
    }
    @PutMapping
    @HystrixCommand(fallbackMethod = "testFallbackMethod")
    public String test4(UserModel user){
        System.err.println("=========userPut==============");
        return "eureka-client"+user.getName();
    }

    @DeleteMapping
    @HystrixCommand(fallbackMethod = "testFallbackMethod")
    public String test5(UserModel user){
        System.err.println("=========userDelete==============");
        return "eureka-client"+user.getIds();
    }

}
