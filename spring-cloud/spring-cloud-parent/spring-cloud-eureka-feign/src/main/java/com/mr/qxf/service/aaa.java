package com.mr.qxf.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value="eureka-client",contextId = "aaa")
public interface aaa {
    @GetMapping(value="eureka-client-controller/testUrl")
    String testUrl();
}
