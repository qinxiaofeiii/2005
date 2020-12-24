package com.mr.qxf.user.service;

import com.mr.qxf.user.model.UserModel;

import java.util.List;

public interface UserServiceI {
    List<UserModel> list();

    void save(UserModel user);

    void delData(Integer id);

    UserModel findById(Integer id);
}
