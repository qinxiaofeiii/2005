package com.mr.qxf.dog.controller;

import com.mr.qxf.dog.model.DogModel;
import com.mr.qxf.dog.service.DogServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping(value="dog")
public class DogController {
    @Autowired
    private DogServiceI service;

    @GetMapping
    public String list(ModelMap map){
        List<DogModel> list = service.list();
        map.put("dogList",list);
        return "list";
    }
    @GetMapping(value="toSave")
    public String toSave(){
        return "save";
    }

    @PostMapping
    public String save(DogModel dog){
        service.saveOrUpdate(dog);
        return "redirect:/dog";
    }
    @GetMapping(value="delData")
    public String delData(Integer id){
        service.delData(id);
        return "redirect:/dog";
    }
    @GetMapping(value="findById")
    public ModelAndView findById(Integer id,ModelAndView mav){
        DogModel dog = service.findById(id);
        mav.addObject("dog",dog);
        mav.setViewName("save");
        return mav;
    }

}
