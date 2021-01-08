package com.baidu.shop.dto;

import com.baidu.shop.base.BaseDTO;
import com.baidu.shop.validate.group.MingruiOperation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@ApiModel(value = "规格参数DTO")
@Data
public class SpecParamDTO extends BaseDTO {

    @ApiModelProperty(value = "规格参数主键",example = "1")
    @NotNull(message = "规格参数不能为空",groups = {MingruiOperation.Update.class})
    private Integer id;

    @NotNull(message = "商品分类主键不能为空",groups = {MingruiOperation.Add.class,MingruiOperation.Update.class})
    @ApiModelProperty(value = "商品分类主键",example = "1")
    private Integer cid;

    @NotNull(message = "规格组主键不能为空",groups = {MingruiOperation.Add.class,MingruiOperation.Update.class})
    @ApiModelProperty(value = "规格组主键",example = "1")
    private Integer groupId;

    @ApiModelProperty(value = "规格参数名")
    @NotNull(message = "规格参数名不能为空",groups = {MingruiOperation.Add.class,MingruiOperation.Update.class})
    private String name;

    @Column(name = "`numeric`")
    @NotNull(message = "是否是数字类型参数不能为空",groups = {MingruiOperation.Add.class,MingruiOperation.Update.class})
    @ApiModelProperty(value = "是否是数字类型参数,true或者false")
    private Boolean numeric;

    @ApiModelProperty(value = "数字类型参数的单位,非数字参数类型单位可以为空")
    private String unit;

    @ApiModelProperty(value = "是否是sku通用属性，true或false")
    @NotNull(message = "是否是sku通用属性不能为空",groups = {MingruiOperation.Add.class,MingruiOperation.Update.class})
    private Boolean generic;

    @NotNull(message = "是否用于搜索过滤不能为空",groups = {MingruiOperation.Add.class,MingruiOperation.Update.class})
    @ApiModelProperty(value = "是否用于搜索过滤，true或false")
    private Boolean searching;

    @NotNull(message = "数值类型参数不能为空",groups = {MingruiOperation.Add.class,MingruiOperation.Update.class})
    @ApiModelProperty(value = "数值类型参数，如果需要搜索，则添加分段间隔值，如CPU频率间隔：0.5-1.0")
    private String segments;
}
