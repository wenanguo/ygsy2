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
 * @since 2021-02-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="SsWaterstationOrderDetail对象", description="订单详情")
public class SsWaterstationOrderDetail extends BaseEntity implements Serializable {

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
    private Double price;

//    @ApiModelProperty(value = "支付方式 1-水票，2-微信，3-水到付款")
//    private String payWay;

    @ApiModelProperty(value = "备注")
    private String memo;

    @ApiModelProperty(value = "金额小计")
    private Double amount;

//    @ApiModelProperty(value = "用户配送地址")
//    private Integer addressId;
//
//    private Integer stationId;


}
