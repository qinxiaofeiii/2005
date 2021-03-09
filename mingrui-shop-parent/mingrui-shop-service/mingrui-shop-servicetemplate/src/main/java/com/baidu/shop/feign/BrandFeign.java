package com.baidu.shop.feign;

import com.baidu.shop.service.BrandServiceI;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "xxx-server",contextId ="BrandFeign")
public interface BrandFeign extends BrandServiceI {

}
