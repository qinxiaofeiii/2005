package com.mr.qxf.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;


@FeignClient(value = "eureka-feign")
public interface TestEurekaClientFeignI {
    //4
    @GetMapping(value="eureka-feign-zipkin-controller/method2",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    String method2();
}
