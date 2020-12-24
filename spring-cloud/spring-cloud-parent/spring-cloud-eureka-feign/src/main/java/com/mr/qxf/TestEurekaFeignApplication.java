package com.mr.qxf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.TreeMap;
import java.util.TreeSet;

@SpringBootApplication
@EnableFeignClients//声明当前服务是一个feign客户端,同时扫描FeignCl
@EnableEurekaClient
public class TestEurekaFeignApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestEurekaFeignApplication.class);
        new TreeSet<>();
        new TreeMap<>();
    }
}
