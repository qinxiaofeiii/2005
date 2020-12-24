package com.mr.qxf.dog.mapper;

import com.mr.qxf.dog.model.DogModel;

import java.util.List;

public interface DogModelMapper {
    int deleteByPrimaryKey(Integer dogId);

    int insert(DogModel record);

    int insertSelective(DogModel record);

    DogModel selectByPrimaryKey(Integer dogId);

    int updateByPrimaryKeySelective(DogModel record);

    int updateByPrimaryKey(DogModel record);

    List<DogModel> list();
}