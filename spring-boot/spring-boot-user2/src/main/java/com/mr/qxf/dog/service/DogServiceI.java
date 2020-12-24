package com.mr.qxf.dog.service;

import com.mr.qxf.dog.model.DogModel;

import java.util.List;

public interface DogServiceI {
    List<DogModel> list();

    void saveOrUpdate(DogModel dog);

    void delData(Integer id);

    DogModel findById(Integer id);
}
