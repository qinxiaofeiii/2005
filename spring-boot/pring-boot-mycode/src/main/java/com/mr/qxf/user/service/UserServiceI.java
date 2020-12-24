package com.mr.qxf.user.service;

import com.mr.qxf.user.model.UserModel;

import java.util.List;

public interface UserServiceI {

    List<UserModel> getUserList();

    void save(UserModel user);

    void delete(Integer id);

    List<UserModel> queryById(Integer id);

    void update(UserModel user);
}
