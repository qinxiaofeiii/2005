package com.baidu.shop.controller;


import com.baidu.shop.serviceI.PageServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

//@Controller
//@RequestMapping(value = "item")
public class PageController {

    @Autowired
    private PageServiceI pageServiceI;

    @GetMapping(value = "{spuId}.html")
    public String test(@PathVariable(value = "spuId") Integer spuId, ModelMap modelMap){
       Map<String,Object> map = pageServiceI.getGoodsInfo(spuId);
        modelMap.putAll(map);
        return "item";
    }
}
