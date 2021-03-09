package com.mr.entity;


public class Student {
    private String code;
    private String pass;
    private int age;
    private String likeColor;

    public Student(String code, String pass, int age, String likeColor) {
        this.code = code;
        this.pass = pass;
        this.age = age;
        this.likeColor = likeColor;
    }

    public Student() {
    }

    @Override
    public String toString() {
        return "Student{" +
                "code='" + code + '\'' +
                ", pass='" + pass + '\'' +
                ", age=" + age +
                ", likeColor='" + likeColor + '\'' +
                '}';
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getLikeColor() {
        return likeColor;
    }

    public void setLikeColor(String likeColor) {
        this.likeColor = likeColor;
    }
}
