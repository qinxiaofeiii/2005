package com.baidu.shop.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baidu.shop.base.BaseApiService;
import com.baidu.shop.base.Result;
import com.baidu.shop.dto.SpecGroupDTO;
import com.baidu.shop.dto.SpecParamDTO;
import com.baidu.shop.entity.SpecGroupEntity;
import com.baidu.shop.entity.SpecParamEntity;
import com.baidu.shop.mapper.SpecGroupMapper;
import com.baidu.shop.mapper.SpecParamMapper;
import com.baidu.shop.service.SpecificationServiceI;
import com.baidu.shop.utils.BaiduBeanUtil;
import com.baidu.shop.utils.ObjectUtil;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
@RestController
public class SpecificationServiceImpl extends BaseApiService implements SpecificationServiceI {

    @Resource
    private SpecGroupMapper specGroupMapper;

    @Resource
    private SpecParamMapper specParamMapper;

    @Override
    public Result<List<JSONObject>> saveSpecParam(SpecParamDTO specParamDTO) {

        specParamMapper.insertSelective(BaiduBeanUtil.copyProperties(specParamDTO,SpecParamEntity.class));

        return this.setResultSuccess();
    }

    @Override
    public Result<List<JSONObject>> editSpecParam(SpecParamDTO specParamDTO) {

        specParamMapper.updateByPrimaryKeySelective(BaiduBeanUtil.copyProperties(specParamDTO,SpecParamEntity.class));

        return this.setResultSuccess();
    }

    @Override
    public Result<List<JSONObject>> deleteSpecParam(Integer id) {

        specParamMapper.deleteByPrimaryKey(id);

        return this.setResultSuccess();
    }

    @Override
    @Transactional
    public Result<List<SpecParamEntity>> getSpecParamByGroupId(SpecParamDTO specParamDTO) {

        Example example = new Example(SpecParamEntity.class);
        Example.Criteria criteria = example.createCriteria();

        if(ObjectUtil.isNotNull(specParamDTO.getGroupId()))
            criteria.andEqualTo("groupId",BaiduBeanUtil.copyProperties(specParamDTO,SpecParamEntity.class).getGroupId());
        if(ObjectUtil.isNotNull(specParamDTO.getCid()))
            criteria.andEqualTo("cid",specParamDTO.getCid());

        List<SpecParamEntity> specParamEntities = specParamMapper.selectByExample(example);

        return this.setResultSuccess(specParamEntities);
    }

    @Override
    @Transactional
    public Result<JSONObject> saveSpecGroup(SpecGroupDTO specGroupDTO) {

        specGroupMapper.insertSelective(BaiduBeanUtil.copyProperties(specGroupDTO,SpecGroupEntity.class));

        return this.setResultSuccess();
    }

    @Override
    @Transactional
    public Result<JSONObject> updateSpecGroup(SpecGroupDTO specGroupDTO) {

        specGroupMapper.updateByPrimaryKeySelective(BaiduBeanUtil.copyProperties(specGroupDTO,SpecGroupEntity.class));

        return this.setResultSuccess();
    }

    @Override
    @Transactional
    public Result<JSONObject> deleteSpecGroup(Integer id) {

        Example example = new Example(SpecParamEntity.class);
        example.createCriteria().andEqualTo("groupId",id);

        List<SpecParamEntity> specParamEntities = specParamMapper.selectByExample(example);

        if(specParamEntities.size() >= 1) return this.setResultError("该规格分组下绑定了规格参数,不能直接删除");

        specGroupMapper.deleteByPrimaryKey(id);

        return this.setResultSuccess();
    }

    @Override
    @Transactional
    public Result<List<SpecGroupEntity>> getSpecGroupInfo(SpecGroupDTO specGroupDTO) {

        Example example = new Example(SpecGroupEntity.class);

        example.createCriteria().andEqualTo("cid",BaiduBeanUtil.copyProperties(specGroupDTO,SpecGroupEntity.class).getCid());

        List<SpecGroupEntity> specGroupEntities = specGroupMapper.selectByExample(example);

        return this.setResultSuccess(specGroupEntities);
    }
}
