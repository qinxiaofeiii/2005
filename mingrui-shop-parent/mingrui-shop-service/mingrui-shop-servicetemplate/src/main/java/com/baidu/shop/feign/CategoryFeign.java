package com.baidu.shop.feign;

import com.baidu.shop.service.CategoryServiceI;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "xxx-server",contextId = "CategoryFeign")
public interface CategoryFeign extends CategoryServiceI {
}
