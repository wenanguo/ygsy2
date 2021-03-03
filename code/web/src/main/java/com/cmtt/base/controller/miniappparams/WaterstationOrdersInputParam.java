package com.cmtt.base.controller.miniappparams;

import com.baomidou.mybatisplus.annotation.TableField;
import com.cmtt.base.entity.SsOrderDetail;
import com.cmtt.base.entity.SsWaterStation;
import com.cmtt.base.entity.SsWaterstationOrderDetail;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/*********************************
 * @author andrew
 * @version v2.0
 * 水站端订单
 *********************************/
@Data
public class WaterstationOrdersInputParam extends PageInputParam{

    @ApiModelProperty(value = "水站编号")
    private Integer stationId;

    @ApiModelProperty(value = "订单编号")
    private String orderNumber;

    @ApiModelProperty(value = "订单状态 1-待分配，2-配送中，3-已完成，4-已取消")
    private Integer orderStatus;

    @ApiModelProperty(value = "订单总金额")
    private double totalPrice;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "预约时间")
    @JsonFormat(timezone = "GMT+8", shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
   // @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private String appointTime;

    @ApiModelProperty(value = "配送表-配送状态 0-已接单，1-配送中，2-已配送，3-已取消")
    @TableField(exist = false)
    private String ssOrderDeliveryStatus;

    @ApiModelProperty(value = "订单详情表-数量")
    @TableField(exist = false)
    private String ssOrderDetailNum;

    @ApiModelProperty(value = "订单详情表-支付方式 1-水票，2-微信，3-水到付款")
    private String payWay;

    @ApiModelProperty(value = "支付状态：201-未支付,202-支付中,203-支付成功,204-支付失败")
    private Integer payStatus;

    @ApiModelProperty(value = "商品表-商品类型 1-水票 2-水")
    @TableField(exist = false)
    private String goodsTypeId;

    @ApiModelProperty(value = "商品表-水票ID")
    @TableField(exist = false)
    private String ticketId;

    @ApiModelProperty(value = "商品表-水票张数，类型为水票时，填写张数")
    @TableField(exist = false)
    private String ticketNum;


    @ApiModelProperty(value = "商品表-商品图片")
    @TableField(exist = false)
    private String goodsImg;


    @ApiModelProperty(value = "商品表-商品id")
    @TableField(exist = false)
    private Integer goodsId;

    @ApiModelProperty(value = "订单详情表-地址id")
    @TableField(exist = false)
    private Integer addressId;

    @ApiModelProperty(value = "订单详情表-对象")
    @TableField(exist = false)
    private List<SsWaterstationOrderDetail> ssWaterstationOrderDetail;

    @ApiModelProperty(value = "订单详情表-商品数量")
    @TableField(exist = false)
    private Integer num;

    @ApiModelProperty(value = "批量订单传入的数量")
    @TableField(exist = false)
    private Integer totalCount;

}
