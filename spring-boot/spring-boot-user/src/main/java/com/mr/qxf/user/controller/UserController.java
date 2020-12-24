package com.mr.qxf.user.controller;

import com.mr.qxf.user.model.UserModel;
import com.mr.qxf.user.service.UserServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping(value="user")
public class UserController {
    @Autowired
    private UserServiceI userService;

    @GetMapping
    public String list(ModelMap map){
        List<UserModel> list = userService.list();
        map.put("userList",list);
        return "list";
    }
    @GetMapping(value="toAdd")
    public String list(){
        return "add";
    }
    @PostMapping
    public String save(UserModel user){
        userService.save(user);
        return "redirect:/user";
    }
    @GetMapping(value="delData")
    public String delData(Integer id){
        userService.delData(id);
        return "redirect:/user";
    }
    @GetMapping(value="update")
    public ModelAndView update(Integer id,ModelAndView mav){
        UserModel user = userService.findById(id);
        mav.addObject("user",user);
        mav.setViewName("add");
        return mav;
    }
}
