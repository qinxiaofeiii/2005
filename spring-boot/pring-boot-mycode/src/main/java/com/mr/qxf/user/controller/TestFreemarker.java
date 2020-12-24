package com.mr.qxf.user.controller;

import com.mr.qxf.user.model.UserModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping(value="free")
public class TestFreemarker {
//    @GetMapping(value="str")
//    public String testFreemarker (){
//
//        return "index";
//    }

//    @GetMapping(value="view")
//    public ModelAndView testFreemarker (ModelAndView view){
//        view.addObject("username","张三");
//        view.addObject("age",18);
//        view.setViewName("list");
//        return view;
//    }
    @GetMapping(value="map")
    public String testFreemarker(ModelMap map){
        List<String> list = new ArrayList<>();
        list.add("张三");
        list.add("20");
        list.add("男");
        map.put("userList",list);
        return "user";
    }
//    @GetMapping(value="list")
//    public String testUser(ModelMap map){
//          List<UserModel> list = new ArrayList<>();
//          UserModel userModel = new UserModel();
//        UserModel userModel2 = new UserModel(2,"王麻子",18);
//        UserModel userModel3 = new UserModel(3,"赵铁柱",23);
//        UserModel userModel4 = new UserModel(3,"张三",20);
//        list.add(userModel);
//        list.add(userModel2);
//        list.add(userModel3);
//        list.add(userModel4);
//        for (int i = 0; i < 5; i++) {
//            UserModel userModel = new UserModel(1+i,"name"+i,20+i);
//            list.add(userModel);
//        }
//        map.put("userList",list);
//        return "user";
//    }

}
