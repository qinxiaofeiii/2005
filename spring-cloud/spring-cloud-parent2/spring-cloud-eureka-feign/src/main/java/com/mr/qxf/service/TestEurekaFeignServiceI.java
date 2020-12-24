package com.mr.qxf.service;

import com.mr.qxf.model.UserModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@FeignClient(value="eureka-client")
public interface TestEurekaFeignServiceI {

    @GetMapping(value="eureka-client-controller",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    String test(@RequestParam String name);

    @GetMapping(value="eureka-client-controller/user",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    String test2(@SpringQueryMap UserModel user);

    @PostMapping(value="eureka-client-controller",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    String test3(@SpringQueryMap UserModel user);

    @PutMapping(value="eureka-client-controller",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    String test4(@SpringQueryMap UserModel user);

    @DeleteMapping(value="eureka-client-controller",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    String test5(@RequestParam String ids);
}
