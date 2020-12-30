package com.baidu.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;

@Table(name = "tb_category_brand")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryBrandEntity {

    private Integer categoryId;

    private Integer brandId;
}
