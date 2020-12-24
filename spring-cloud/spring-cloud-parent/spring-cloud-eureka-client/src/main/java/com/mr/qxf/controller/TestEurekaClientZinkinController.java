package com.mr.qxf.controller;

import com.mr.qxf.feign.TestEurekaClientFeignI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "eureka-client-zipkin-controller")
public class TestEurekaClientZinkinController {
    @Autowired
    private TestEurekaClientFeignI clientFeign;

    //3
    @GetMapping
    public String method(){
        clientFeign.method2();
        System.err.println("============method方法调用成功===========");
        return "method";
    }
}
