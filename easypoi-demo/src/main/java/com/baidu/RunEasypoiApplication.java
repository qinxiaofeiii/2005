package com.baidu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.baidu.easypoi.mapper")
public class RunEasypoiApplication {
    public static void main(String[] args) {
        SpringApplication.run(RunEasypoiApplication.class);
    }
}
