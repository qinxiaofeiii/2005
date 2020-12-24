package com.mr.qxf.user.mapper;

import com.mr.qxf.user.model.UserModel;

import java.util.List;

public interface UserModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserModel record);

    int insertSelective(UserModel record);

    List<UserModel> selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserModel record);

    int updateByPrimaryKey(UserModel record);

    List<UserModel> getUserList();
}