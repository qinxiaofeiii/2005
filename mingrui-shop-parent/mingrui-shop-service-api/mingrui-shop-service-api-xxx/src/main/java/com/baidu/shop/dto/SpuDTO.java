package com.baidu.shop.dto;

import com.baidu.shop.base.BaseDTO;
import com.baidu.shop.validate.group.MingruiOperation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@ApiModel(value = "Spu数据传输DTO")
@Data
public class SpuDTO extends BaseDTO {

    @ApiModelProperty(value = "主键",example = "1")
    @NotNull(message = "spu主键不能为空",groups = {MingruiOperation.Update.class})
    private Integer id;

    @ApiModelProperty(value = "标题")
    @NotNull(message = "标题不能为空",groups = {MingruiOperation.Add.class,MingruiOperation.Update.class})
    private String title;

    @NotNull(message = "子标题不能为空",groups = {MingruiOperation.Add.class,MingruiOperation.Update.class})
    @ApiModelProperty(value = "子标题")
    private String subTitle;

    @ApiModelProperty(value = "1级类目id",example = "1")
    @NotNull(message = "1级类目id不能为空",groups = {MingruiOperation.Add.class,MingruiOperation.Update.class})
    private Integer cid1;

    @ApiModelProperty(value = "2级类目id",example = "1")
    @NotNull(message = "2级类目id不能为空",groups = {MingruiOperation.Add.class,MingruiOperation.Update.class})
    private Integer cid2;

    @ApiModelProperty(value = "3级类目id",example = "1")
    @NotNull(message = "3级类目id不能为空",groups = {MingruiOperation.Add.class,MingruiOperation.Update.class})
    private Integer cid3;

    @ApiModelProperty(value = "商品所属品牌id",example = "1")
    @NotNull(message = "商品所属品牌id不能为空",groups = {MingruiOperation.Add.class,MingruiOperation.Update.class})
    private Integer brandId;

    @NotNull(message = "商品是否上下架不能为空")
    @ApiModelProperty(value = "是否上架，0下架，1上架",example = "1")
    private Integer saleable;

    @NotNull(message = "商品是否有效不能为空")
    @ApiModelProperty(value = "是否有效，0已删除，1有效",example = "1")
    private Integer valid;

    @NotNull(message = "创建时间不能为空")
    @ApiModelProperty(value = "添加时间")
    private Date createTime;

    @NotNull(message = "最后修改时间不能为空")
    @ApiModelProperty(value = "最后修改的时间")
    private Date lastUpdateTime;

    private String categoryName;

    private String brandName;

    @NotNull(message = "spuDetail不能为空",groups = {MingruiOperation.Add.class,MingruiOperation.Update.class})
    @ApiModelProperty(value = "大字段数据")
    private SpuDetailDTO spuDetail;

    @ApiModelProperty(value = "sku属性数据集合")
    private List<SkuDTO> skus;

}
