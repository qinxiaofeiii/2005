package com.baidu.shop.service.impl;

import com.baidu.shop.base.BaseApiService;
import com.baidu.shop.base.Result;
import com.baidu.shop.entity.CategoryBrandEntity;
import com.baidu.shop.entity.CategoryEntity;
import com.baidu.shop.mapper.CategoryBrandMapper;
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

    @Resource
    private CategoryBrandMapper categoryBrandMapper;

    @Override
    @Transactional
    public Result<List<CategoryEntity>> getCategoryByBrandId(Integer brandId) {

        List<CategoryEntity> list = categoryMapper.getCategoryByBrandId(brandId);

        return this.setResultSuccess(list);
    }

    @Transactional//控制事务
    @Override
    public Result<JsonObject> saveCategoryNameById(CategoryEntity categoryEntity) {

        //根据新增节点的父id查询父节点信息
        CategoryEntity parentCategoryEntity = categoryMapper.selectByPrimaryKey(categoryEntity.getParentId());
        //判断新增节点的父节点的状态是不是父节点
        if(parentCategoryEntity.getIsParent() != 1) {

            CategoryEntity categoryEntity1 = new CategoryEntity();
            categoryEntity1.setId(categoryEntity.getParentId());
            categoryEntity1.setIsParent(1);
            //如果父节点的状态不是父节点,将其修改为父节点
            categoryMapper.updateByPrimaryKeySelective(categoryEntity1);

        }
        //新增节点
        categoryMapper.insertSelective(categoryEntity);

        return this.setResultSuccess();
    }

    @Override
    public Result<List<CategoryEntity>> getCategoryByPid(Integer pid) {

        CategoryEntity categoryEntity = new CategoryEntity();

        categoryEntity.setParentId(pid);

        List<CategoryEntity> list = categoryMapper.select(categoryEntity);

        return this.setResultSuccess(list);
    }

    @Override
    @Transactional //控制事务
    public Result<JsonObject> deleteCategoryById(Integer id) {
        //id校验
        if(ObjectUtil.isNull(id) || id <= 0) return this.setResultError(HTTPStatus.OPERATION_ERROR,"id不合法");

        //通过id查询当前节点数据
        CategoryEntity categoryEntity = categoryMapper.selectByPrimaryKey(id);

        //判断当前数据是否为空
        if(ObjectUtil.isNull(categoryEntity)) return this.setResultError(HTTPStatus.OPERATION_ERROR,"数据不存在");

        //判断当前节点是否为父节点
        if(categoryEntity.getIsParent() == 1) return this.setResultError(HTTPStatus.OPERATION_ERROR,"当前节点为父节点,不能被删除");

        //如果当前分类被品牌绑定,该分类不能被删除
        Example example1 = new Example(CategoryBrandEntity.class);
        example1.createCriteria().andEqualTo("categoryId",id);
        List<CategoryBrandEntity> select = categoryBrandMapper.selectByExample(example1);

        if(select.size() >= 1) return this.setResultError("该分类已经被其他品牌绑定,不能被删除");

        //查询当前节点的父节点下有没有其他子节点
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
}
