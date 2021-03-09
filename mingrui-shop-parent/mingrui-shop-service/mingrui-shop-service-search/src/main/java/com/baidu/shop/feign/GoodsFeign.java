package com.baidu.shop.feign;

import com.baidu.shop.service.GoodsServiceI;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "xxx-server",contextId = "GoodsFeign")
public interface GoodsFeign extends GoodsServiceI {
}
