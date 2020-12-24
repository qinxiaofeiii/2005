package com.mr.qxf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author qin132112
 */
@SpringBootApplication
@SpringBootConfiguration
@MapperScan(value="com.mr.qxf.user.mapper")
public class TestSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestSpringBootApplication.class);
    }
}
