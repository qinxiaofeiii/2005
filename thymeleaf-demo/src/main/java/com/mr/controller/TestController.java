package com.mr.controller;

import com.mr.entity.Student;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Controller
public class TestController {
    @GetMapping("hello")//定义url使用modelmap返回数据
    public String test(ModelMap map){
        map.put("name","tomcat");
        return "hello";
    }

    @GetMapping("student")
    public String student(ModelMap map){
        Student student=new Student();
        student.setCode("007");
        student.setPass("9527");
        student.setAge(18);
        student.setLikeColor("<font color='red'>红色</font>");
        map.put("stu",student);
        return "student";
    }

    @GetMapping("list")
    public String list(ModelMap map){
        Student s1=new Student("001","111",18,"red");
        Student s2=new Student("002","222",19,"red");
        Student s3=new Student("003","333",16,"blue");
        Student s4=new Student("004","444",28,"blue");
        Student s5=new Student("005","555",68,"blue");
            //转为List
            map.put("stuList", Arrays.asList(s1,s2,s3,s4,s5));
            return "list";
    }

    @GetMapping("each")
    public String each(Model model){

        List<Student> students = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            students.add(new Student("1"+i, "a"+ i, 15 +i, "red" + 1));
        }
        model.addAttribute("students",students);
        return "each";
    }



}
