package com.cmtt.base.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * 订单详情
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-01-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="SsOrderDetail对象", description="订单详情")
public class SsOrderDetail extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "序号")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "订单编号")
    private String orderNumber;

    @ApiModelProperty(value = "商品ID")
    private Integer goodsId;

    @ApiModelProperty(value = "数量")
    private Integer num;

    @ApiModelProperty(value = "价格")
    private double price;


    @ApiModelProperty(value = "备注")
    private String memo;

    @ApiModelProperty(value = "金额小计")
    private double amount;

    @ApiModelProperty(value = "用户配送货地址")
    private Integer addressId;

    @ApiModelProperty(value = "用户水站id")
    private Integer stationId;
}
