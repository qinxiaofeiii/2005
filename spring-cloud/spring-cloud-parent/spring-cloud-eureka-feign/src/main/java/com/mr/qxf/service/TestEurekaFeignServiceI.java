package com.mr.qxf.service;

import com.mr.qxf.model.UserModel;
import com.mr.qxf.service.fallback.TestEurekaFeignFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@FeignClient(value="eureka-client",fallback = TestEurekaFeignFallBack.class)
public interface TestEurekaFeignServiceI {

    //2
    @GetMapping(value = "eureka-client-zipkin-controller",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    String method();

    @GetMapping(value="eureka-client-controller")
    String test(@RequestParam String name);

    @GetMapping(value="eureka-client-controller/user",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    String test2(@SpringQueryMap UserModel user);

    @PostMapping(value="eureka-client-controller", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    String test3(@SpringQueryMap UserModel user);

    @PutMapping(value="eureka-client-controller", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    String test4(@SpringQueryMap UserModel user);

    @DeleteMapping(value="eureka-client-controller", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    String test5(@RequestParam String ids);


}
