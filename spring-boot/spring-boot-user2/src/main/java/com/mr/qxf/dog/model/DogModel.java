package com.mr.qxf.dog.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class DogModel {
    private Integer dogId;

    private String dogName;

    private Integer dogSex;

    private Integer dogType;

    private String dogFood;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dogBir;

    public Integer getDogId() {
        return dogId;
    }

    public void setDogId(Integer dogId) {
        this.dogId = dogId;
    }

    public String getDogName() {
        return dogName;
    }

    public void setDogName(String dogName) {
        this.dogName = dogName == null ? null : dogName.trim();
    }

    public Integer getDogSex() {
        return dogSex;
    }

    public void setDogSex(Integer dogSex) {
        this.dogSex = dogSex;
    }

    public Integer getDogType() {
        return dogType;
    }

    public void setDogType(Integer dogType) {
        this.dogType = dogType;
    }

    public String getDogFood() {
        return dogFood;
    }

    public void setDogFood(String dogFood) {
        this.dogFood = dogFood == null ? null : dogFood.trim();
    }

    public Date getDogBir() {
        return dogBir;
    }

    public void setDogBir(Date dogBir) {
        this.dogBir = dogBir;
    }
}