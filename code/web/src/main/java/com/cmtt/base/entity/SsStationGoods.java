package com.cmtt.base.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.cmtt.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;

/**
 * <p>
 * 水站商品信息
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-01-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="SsStationGoods对象", description="水站商品信息")
public class SsStationGoods extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "序号")
    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "水站编号")
    private Integer stationId;

    @ApiModelProperty(value = "商品ID")
    private Integer goodsId;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @TableField(exist = false)
    @ApiModelProperty(value = "商品ID数组")
    private String[] goodsIdArray;

    @TableField(exist = false)
    @ApiModelProperty(value = "水站名称")
    private String stationName;

    @TableField(exist = false)
    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @TableField(exist = false)
    @ApiModelProperty(value = "成本价")
    private Double costPrice;

    @TableField(exist = false)
    @ApiModelProperty(value = "单价，即销售价")
    private Double unitPrice;

    @ApiModelProperty(value = "水站销售价")
    private Double stationSalePrice;

}
