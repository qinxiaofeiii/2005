package com.mr.qxf.user.service.impl;

import com.mr.qxf.user.mapper.UserModelMapper;
import com.mr.qxf.user.model.UserModel;
import com.mr.qxf.user.service.UserServiceI;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserServiceI{
    @Resource
    private UserModelMapper mapper;

    @Override
    public List<UserModel> list() {
        return mapper.list();
    }

    @Override
    public void save(UserModel user) {
        if(null != user.getId()){
            mapper.updateByPrimaryKeySelective(user);
        }else{
            mapper.insertSelective(user);
        }
    }

    @Override
    public void delData(Integer id) {
        mapper.deleteByPrimaryKey(id);
    }

    @Override
    public UserModel findById(Integer id) {
        return mapper.selectByPrimaryKey(id);
    }
}
