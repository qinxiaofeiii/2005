package com.mr.qxf.user.controller;

import com.mr.qxf.user.model.UserModel;
import com.mr.qxf.user.service.UserServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;


/**
 * @author qin132112
 */
@Controller
@RequestMapping(value="user")
public class UserController {
    @Autowired
    private UserServiceI service;

    @GetMapping
    public String getUserList(ModelMap map){
        List<UserModel> list =  service.getUserList();
        map.put("userList",list);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        list.stream().forEach(user -> {
//            if(user.getBirthday() != null) user.setDateStr(sdf.format(user.getBirthday()));
//        });
        return "index";
    }

    @GetMapping(value="jumpAdd")
    public ModelAndView jump(ModelAndView mv){
        mv.setViewName("addUser");
        return mv;
    }

    @PostMapping
    public String add(UserModel user){
        service.save(user);
        return "redirect:/user";
    }
    @RequestMapping(value="delete")
    public String delete(Integer id){
        service.delete(id);
        return "redirect:/user";
    }
    @RequestMapping(value="queryById")
    public String queryById(Integer id,ModelMap map){
        List<UserModel> list = service.queryById(id);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        map.put("userList",list);
        list.stream().forEach(user -> {
            if(user.getBirthday() != null) user.setDateStr(sdf.format(user.getBirthday()));
        });
        return "updateUser";
    }
    @RequestMapping(value="update")
    public String update(UserModel user) throws ParseException {
        service.update(user);
        return "redirect:/user";
    }
}
