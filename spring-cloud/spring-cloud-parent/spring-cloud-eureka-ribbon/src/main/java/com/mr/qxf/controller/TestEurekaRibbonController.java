package com.mr.qxf.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value="eureka-ribbon-controller")
public class TestEurekaRibbonController {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping
    @HystrixCommand(fallbackMethod = "testError")
    public String test(String name){
        //http://eureka客户端服务名/模块名
        String url = "http://eureka-client/eureka-client-controller?name="+name;
        String forObject = restTemplate.getForObject(url,String.class);
        return forObject;
    }

    public String testError(String name){
        System.err.println("==========ribbonError=========");
        return "error"+name;
    }
}
