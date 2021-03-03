package com.cmtt.base.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.SqlCondition;
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
import java.util.List;

/**
 * <p>
 * 订单信息
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-02-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="SsWaterstationOrderInfo对象", description="订单信息")
public class SsWaterstationOrderInfo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "序号")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "订单编号")
    private String orderNumber;

    @ApiModelProperty(value = "订单状态 1-待分配，2-配送中，3-已完成，4-已取消")
    private Integer orderStatus;

    @ApiModelProperty(value = "客户ID")
    private String customerId;

    @ApiModelProperty(value = "订单总金额")
    private Double totalPrice;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "下单时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderDate;

    @ApiModelProperty(value = "预约时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime appointTime;

    @ApiModelProperty(value = "支付状态：201-未支付,202-支付中,203-支付成功,204-支付失败")
    private Integer payStatus;

    @ApiModelProperty(value = "支付方式 1-水票，2-微信，3-水到付款")
    private String payWay;

    @ApiModelProperty(value = "水站id")
    private Integer stationId;

    //
    @ApiModelProperty(value = "顾客姓名")
    @TableField(exist = false,condition = SqlCondition.LIKE)
    private String customerName;

    //
    @ApiModelProperty(value = "顾客电话")
    @TableField(exist = false,condition = SqlCondition.LIKE)
    private String customerPhone;

    //
    @ApiModelProperty(value = "顾客性别")
    @TableField(exist = false)
    private String customerSex;

    @ApiModelProperty(value = "配送员姓名")
    @TableField(exist = false)
    private String deleveryUsername;

    @ApiModelProperty(value = "配送员电话")
    @TableField(exist = false)
    private String deleveryPhone;

    @ApiModelProperty(value = "配送表-配送状态 0-已接单，1-配送中，2-已配送，3-已取消")
    @TableField(exist = false)
    private String ssOrderDeliveryStatus;

    @ApiModelProperty(value = "订单详情表-数量")
    @TableField(exist = false)
    private String ssOrderDetailNum;

    @ApiModelProperty(value = "订单详情表价格")
    @TableField(exist = false)
    private String ssOrderDetailPrice;



    @ApiModelProperty(value = "订单详情表-金额小计")
    @TableField(exist = false)
    private String amount;

    @ApiModelProperty(value = "商品表-商品名称")
    @TableField(exist = false)
    private String goodsName;

    @ApiModelProperty(value = "商品表-商品识别码")
    @TableField(exist = false)
    private String goodsCode;

    @ApiModelProperty(value = "商品表-商品类型 1-水票 2-水")
    @TableField(exist = false)
    private String goodsTypeId;

    @ApiModelProperty(value = "商品表-水票ID")
    @TableField(exist = false)
    private String ticketId;

    @ApiModelProperty(value = "商品表-水票张数，类型为水票时，填写张数")
    @TableField(exist = false)
    private String ticketNum;

    @ApiModelProperty(value = "商品表-单位 1-桶")
    @TableField(exist = false)
    private String unit;

    @ApiModelProperty(value = "商品表-单价 即销售价")
    @TableField(exist = false)
    private String unitPrice;

    @ApiModelProperty(value = "商品表-成本")
    @TableField(exist = false)
    private String costPrice;

    @ApiModelProperty(value = "商品表-商品图片")
    @TableField(exist = false)
    private String goodsImg;

    @ApiModelProperty(value = "商品表-商品状态 100-已上架，101-已下架")
    @TableField(exist = false)
    private String ssGoodsInfoStatus;

    @ApiModelProperty(value = "用户配送地址表-配送地址")
    @TableField(exist = false)
    private String ssCustomerContactsAddress;

    @ApiModelProperty(value = "用户配送地址表-配送用户姓名")
    @TableField(exist = false)
    private String ssCustomerAddressContacts;

    @ApiModelProperty(value = "用户配送地址表-配送用户电话")
    @TableField(exist = false)
    private String ssCustomerAddressPhone;

    @ApiModelProperty(value = "用户配送地址表-配送用户地址状态 状态100-正常，101-作废")
    @TableField(exist = false)
    private String ssCustomerAddressStatus;

    @ApiModelProperty(value = "水站表-水站名称")
    @TableField(exist = false)
    private String stationName;

    @ApiModelProperty(value = "水站表-水站负责人")
    @TableField(exist = false)
    private String stationLeaderName;

    @ApiModelProperty(value = "水站表-水站负责人电话")
    @TableField(exist = false)
    private String stationPhone;

    @ApiModelProperty(value = "水站表-水站地址")
    @TableField(exist = false)
    private String stationAddress;


    @ApiModelProperty(value = "接单时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime receiveTime;

    @ApiModelProperty(value = "配送时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime deleveryTime;

    @ApiModelProperty(value = "配送完成时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime finishTime;

    @ApiModelProperty(value = "商品表-商品id")
    @TableField(exist = false)
    private Integer goodsId;

    @ApiModelProperty(value = "订单详情表-地址id")
    @TableField(exist = false)
    private Integer addressId;

    @ApiModelProperty(value = "订单详情表-对象")
    @TableField(exist = false)
    private List<SsWaterstationOrderDetail> ssWaterstationOrderDetail;

    @ApiModelProperty(value = "门牌号")
    @TableField(exist = false)
    private String houseNumber;


    @ApiModelProperty(value = "订单开始时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(exist = false)
    private String startTime;

    @ApiModelProperty(value = "订单结束时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(exist = false)
    private String endTime;


}
