package com.mr.qxf.controller;

import com.mr.qxf.service.TestEurekaFeignServiceI;
import com.mr.qxf.service.aaa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "eureka-feign-zipkin-controller")
public class TestEurekaFeignZipkinController {
    @Autowired
    private TestEurekaFeignServiceI feignZipkin;

    @Autowired
    private aaa a;

    @GetMapping(value="a")
    public String test(){
        String s = a.testUrl();
        return s;
    }

    //  1
    @GetMapping(value = "/method1")
    public String method1(){
        feignZipkin.method();
        System.err.println("=============method1方法调用成功==============");
        return "method1";
    }

    //5
    @GetMapping(value = "/method2")
    public String method2(){
        System.err.println("==================method2方法调用成功=====================");
        return "method2";
    }
}
