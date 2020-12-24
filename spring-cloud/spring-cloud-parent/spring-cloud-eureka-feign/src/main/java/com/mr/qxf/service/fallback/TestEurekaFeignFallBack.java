package com.mr.qxf.service.fallback;

import com.mr.qxf.model.UserModel;
import com.mr.qxf.service.TestEurekaFeignServiceI;
import org.springframework.stereotype.Component;

@Component//申明当前类是spring组件
public class TestEurekaFeignFallBack implements TestEurekaFeignServiceI {

    @Override
    public String method() {
        return null;
    }

    @Override
    public String test(String name) {
        return "error" + name;
    }

    @Override
    public String test2(UserModel user) {
        return null;
    }

    @Override
    public String test3(UserModel user) {
        return null;
    }

    @Override
    public String test4(UserModel user) {
        return null;
    }

    @Override
    public String test5(String ids) {
        return null;
    }
}
