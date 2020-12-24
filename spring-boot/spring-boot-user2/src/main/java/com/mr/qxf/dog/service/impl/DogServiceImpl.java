package com.mr.qxf.dog.service.impl;

import com.mr.qxf.dog.mapper.DogModelMapper;
import com.mr.qxf.dog.model.DogModel;
import com.mr.qxf.dog.service.DogServiceI;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DogServiceImpl implements DogServiceI {
    @Resource
    private DogModelMapper mapper;


    @Override
    public List<DogModel> list() {
        return mapper.list();

    }

    @Override
    public void saveOrUpdate(DogModel dog) {
        if(null != dog.getDogId()){
            mapper.updateByPrimaryKeySelective(dog);
        }else{
            mapper.insertSelective(dog);
        }
    }

    @Override
    public void delData(Integer id) {
        mapper.deleteByPrimaryKey(id);
    }

    @Override
    public DogModel findById(Integer id) {
        return mapper.selectByPrimaryKey(id);
    }
}
