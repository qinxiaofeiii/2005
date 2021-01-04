package com.baidu.shop.dto;

import com.baidu.shop.base.BaseDTO;
import com.baidu.shop.validate.group.MingruiOperation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


import javax.validation.constraints.NotNull;

@ApiModel("规格组DTO")
@Data
public class SpecGroupDTO extends BaseDTO {

    @ApiModelProperty(value = "主键",example = "1")
    @NotNull(message = "主键不能为空",groups = {MingruiOperation.Update.class})
    private Integer id;

    @ApiModelProperty(value = "规格组的名称")
    @NotNull(message = "规格组名不能为空",groups = {MingruiOperation.Update.class,MingruiOperation.Add.class})
    private String name;

    @ApiModelProperty(value = "商品分类id，一个分类下有多个规格组",example = "1")
    @NotNull(message = "商品id不能为空",groups = {MingruiOperation.Add.class})
    private Integer cid;
}
