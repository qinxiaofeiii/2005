package com.baidu.shop.service.impl;

import com.baidu.shop.base.BaseApiService;
import com.baidu.shop.base.Result;
import com.baidu.shop.entity.CategoryEntity;
import com.baidu.shop.mapper.CategoryMapper;
import com.baidu.shop.service.CategoryServiceI;
import com.baidu.shop.status.HTTPStatus;
import com.baidu.shop.utils.ObjectUtil;
import com.google.gson.JsonObject;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class CategoryServiceImpl extends BaseApiService implements CategoryServiceI {

    @Resource
    private CategoryMapper categoryMapper;


    @Override
    @Transactional
    public Result<List<CategoryEntity>> getCategoryListByPid(Integer pid) {

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setParentId(pid);

        List<CategoryEntity> list = categoryMapper.select(categoryEntity);
        return this.setResultSuccess(list);
    }

    @Override
    @Transactional
    public Result<JsonObject> deleteCategoryById(Integer id) {
        //id验证
        if(ObjectUtil.isNull(id) || id <= 0) return this.setResultError(HTTPStatus.OPERATION_ERROR,"id不合法");

        CategoryEntity categoryEntity = categoryMapper.selectByPrimaryKey(id);
        //数据验证
        if(ObjectUtil.isNull(categoryEntity)) return this.setResultError(HTTPStatus.OPERATION_ERROR,"数据不存在");
        //验证当前节点是否是父节点
        if(categoryEntity.getIsParent() == 1) return this.setResultError(HTTPStatus.OPERATION_ERROR,"当前节点为父节点,不能被删除");

        Example example = new Example(CategoryEntity.class);
        example.createCriteria().andEqualTo("parentId",categoryEntity.getParentId());
        List<CategoryEntity> list = categoryMapper.selectByExample(example);

        if(list.size() <= 1){

            CategoryEntity updateCategoryEntity = new CategoryEntity();
            updateCategoryEntity.setIsParent(0);
            updateCategoryEntity.setId(categoryEntity.getParentId());

            categoryMapper.updateByPrimaryKeySelective(updateCategoryEntity);
        }

        categoryMapper.deleteByPrimaryKey(id);

        return this.setResultSuccess();
    }

    @Override
    @Transactional
    public Result<JsonObject> updateCategoryNameById(CategoryEntity categoryEntity) {
        categoryMapper.updateByPrimaryKeySelective(categoryEntity);
        return this.setResultSuccess();
    }

    @Override
    @Transactional
    public Result<JsonObject> saveCategoryNameById(CategoryEntity categoryEntity) {

        CategoryEntity parentCategoryEntity = categoryMapper.selectByPrimaryKey(categoryEntity.getParentId());
        if(parentCategoryEntity.getIsParent() != 1) {

            CategoryEntity categoryEntity1 = new CategoryEntity();
            categoryEntity1.setId(categoryEntity.getParentId());
            categoryEntity1.setIsParent(1);

            categoryMapper.updateByPrimaryKeySelective(categoryEntity1);

        }
        //新增
        categoryMapper.insertSelective(categoryEntity);

        return this.setResultSuccess();
    }

    @Override
    public Result<List<CategoryEntity>> getCategoryByBrandId(Integer brandId) {
        List<CategoryEntity> list = categoryMapper.getCategoryByBrandId(brandId);

        return this.setResultSuccess(list);
    }
}
