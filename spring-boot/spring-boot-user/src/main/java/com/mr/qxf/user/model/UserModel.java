package com.mr.qxf.user.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class UserModel {
    private Integer id;

    private String name;

    private Integer sex;

    private String hobby;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    private Integer classs;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby == null ? null : hobby.trim();
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getClasss() {
        return classs;
    }

    public void setClasss(Integer classs) {
        this.classs = classs;
    }
}