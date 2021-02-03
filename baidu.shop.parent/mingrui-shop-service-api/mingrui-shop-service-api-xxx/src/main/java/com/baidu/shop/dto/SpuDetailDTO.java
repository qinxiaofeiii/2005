package com.baidu.shop.dto;

import com.baidu.shop.validate.group.MingruiOperation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "spu传输DTO")
public class SpuDetailDTO {

    @NotNull(message = "spu主键不能为空",groups = {MingruiOperation.Update.class})
    @ApiModelProperty(value = "spu主键",example = "1")
    private Integer spuId;

    @NotNull(message = "商品描述信息不能为空",groups = {MingruiOperation.Update.class,MingruiOperation.Add.class})
    @ApiModelProperty(value = "商品描述信息")
    private String description;

    @NotNull(message = "通用规格参数不能为空",groups = {MingruiOperation.Update.class,MingruiOperation.Add.class})
    @ApiModelProperty(value = "通用规格参数数据")
    private String genericSpec;

    @NotNull(message = "特有规格参数不能为空",groups = {MingruiOperation.Update.class,MingruiOperation.Add.class})
    @ApiModelProperty(value = "特有规格参数及可选值信息，json格式")
    private String specialSpec;

    @NotNull(message = "包装清单不能为空",groups = {MingruiOperation.Update.class,MingruiOperation.Add.class})
    @ApiModelProperty(value = "包装清单")
    private String packingList;

    @NotNull(message = "售后服务不能为空",groups = {MingruiOperation.Update.class,MingruiOperation.Add.class})
    @ApiModelProperty(value = "售后服务")
    private String afterService;

}
