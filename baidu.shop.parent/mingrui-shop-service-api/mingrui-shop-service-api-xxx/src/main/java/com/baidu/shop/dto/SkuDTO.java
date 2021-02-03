package com.baidu.shop.dto;

import com.baidu.shop.validate.group.MingruiOperation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@ApiModel(value = "SKU属性数据DTO")
public class SkuDTO {
    @NotNull(message = "skuId不能为空",groups = {MingruiOperation.Update.class})
    @ApiModelProperty(value = "sku主键", example = "1")
    private Long id;

    @NotNull(message = "spuId不能为空",groups = {MingruiOperation.Update.class,MingruiOperation.Add.class})
    @ApiModelProperty(value = "spu主键", example = "1")
    private Integer spuId;

    @NotNull(message = "商品标题不能为空",groups = {MingruiOperation.Update.class,MingruiOperation.Add.class})
    @ApiModelProperty(value = "商品标题")
    private String title;

    @ApiModelProperty(value = "商品的图片，多个图片以‘,’分割")
    private String images;

    @NotNull(message = "商品价格不能为空",groups = {MingruiOperation.Update.class,MingruiOperation.Add.class})
    @ApiModelProperty(value = "销售价格，单位为分", example = "1")
    private Integer price;

    @ApiModelProperty(value = "特有规格属性在spu属性模板中的对应下标组合")
    private String indexes;

    @ApiModelProperty(value = "sku的特有规格参数键值对，json格式，反序列化时请使用 linkedHashMap，保证有序")
    private String ownSpec;

    //注意此处使用boolean值来接,在service中处理一下就可以了
    @NotNull(message = "商品是否有效不能为空",groups = {MingruiOperation.Update.class,MingruiOperation.Add.class})
    @ApiModelProperty(value = "是否有效，0无效，1有效", example = "1")
    private Boolean enable;

    @NotNull(message = "商品创建时间不能为空",groups = {MingruiOperation.Add.class})
    @ApiModelProperty(value = "添加时间")
    private Date createTime;

    @NotNull(message = "商品修改时间不能为空",groups = {MingruiOperation.Update.class,MingruiOperation.Add.class})
    @ApiModelProperty(value = "最后修改时间")
    private Date lastUpdateTime;

    @ApiModelProperty(value = "库存")
    private Integer stock;

}
