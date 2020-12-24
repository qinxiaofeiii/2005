package com.mr.qxf.user.service.impl;

import com.mr.qxf.user.mapper.UserModelMapper;
import com.mr.qxf.user.model.UserModel;
import com.mr.qxf.user.service.UserServiceI;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserServiceI {
    @Resource
    private UserModelMapper mapper;

    @Override
    public List<UserModel> getUserList() {
        return mapper.getUserList();
    }

    @Override
    public void save(UserModel user) {
        mapper.insert(user);
    }

    @Override
    public void delete(Integer id) {
        mapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<UserModel> queryById(Integer id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(UserModel user) {
        mapper.updateByPrimaryKeySelective(user);
    }
}
