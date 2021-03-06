package com.baidu.shop.dto;

import com.baidu.shop.base.BaseDTO;
import com.baidu.shop.validate.group.MingruiOperation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@ApiModel(value = "品牌DTO")
@Data
public class BrandDTO extends BaseDTO {
    @ApiModelProperty(value = "品牌主键",example = "1")
    @NotNull(message = "品牌主键不能为空",groups = {MingruiOperation.Update.class})
    private Integer id;

    @ApiModelProperty(value = "品牌名称")
    @NotEmpty(message = "品牌名称不能为空",groups = {MingruiOperation.Update.class,MingruiOperation.Add.class})
    private String name;

    @ApiModelProperty(value = "品牌LOGO")
    private String image;

    @ApiModelProperty(value = "品牌首字母")
    @NotEmpty(message = "品牌首字母不能为空",groups = {MingruiOperation.Update.class,MingruiOperation.Add.class})
    private Character letter;

    @ApiModelProperty(value = "商品类别id集合")
    @NotEmpty(message = "商品类别id集合不能为空",groups = {MingruiOperation.Update.class,MingruiOperation.Add.class})
    private String categories;

}
