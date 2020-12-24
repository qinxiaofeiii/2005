package com.mr.qxf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TestEurekaFeignApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestEurekaFeignApplication.class);
    }
}
