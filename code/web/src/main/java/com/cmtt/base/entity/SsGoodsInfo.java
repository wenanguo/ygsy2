package com.cmtt.base.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;

/**
 * <p>
 * 商品信息
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-01-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="SsGoodsInfo对象", description="商品信息")
public class SsGoodsInfo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "序号")
    private Integer id;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "识别码")
    private String goodsCode;

    @ApiModelProperty(value = "商品类型ID")
    private Integer goodsTypeId;

    @ApiModelProperty(value = "单位")
    private String unit;

    @ApiModelProperty(value = "单价 即销售价")
    private Double unitPrice;

    @ApiModelProperty(value = "成本")
    private Double costPrice;

    @ApiModelProperty(value = "水票Id")
    private Integer ticketId;

    @ApiModelProperty(value = "水票张数")
    private Integer ticketNum;

    @ApiModelProperty(value = "排序号")
    private Integer sort;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "商品图片")
    private String goodsImg;

    @ApiModelProperty(value = "商品状态 100-在售，101-下架")
    private Integer status;

    @ApiModelProperty(value = "是否推荐 0-否，1-是")
    private Integer isrecommend;

    @ApiModelProperty(value = "删除标识 0-未删除，1-删除")
    private Integer deleted;

    @ApiModelProperty(value = "失效时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expiredTime;

    @ApiModelProperty(value = "发布时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishTime;

    @ApiModelProperty(value = "创建人ID")
    private Integer createBy;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "最后更新人ID")
    private Integer lastUpdateBy;

    @ApiModelProperty(value = "最后更新时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastUpdateTime;

    @TableField(exist = false)
    @ApiModelProperty(value = "商品类型名称")
    private String typeName;

    @TableField(exist = false)
    @ApiModelProperty(value = "商品关联的水票名称")
    private String ticketName;

    @TableField(exist = false)
    @ApiModelProperty(value = "商品tag串，用^~^分隔")
    private String goodsTagStr;

    @TableField(exist = false)
    @ApiModelProperty(value = "商品订单数量")
    private Integer num;

    @TableField(exist = false)
    @ApiModelProperty(value = "商品订单编号")
    private String orderNumber;

    @TableField(exist = false)
    @ApiModelProperty(value = "商品订单时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderDate;

    @TableField(exist = false)
    @ApiModelProperty(value = "商品订单状态")
    private Integer orderStatus;

    @TableField(exist = false)
    @ApiModelProperty(value = "用户水票数量")
    private Integer customerTicketnum;
}
