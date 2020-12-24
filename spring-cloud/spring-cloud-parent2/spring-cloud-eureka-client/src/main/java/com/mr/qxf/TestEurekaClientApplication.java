package com.mr.qxf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class TestEurekaClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestEurekaClientApplication.class);
    }
}
