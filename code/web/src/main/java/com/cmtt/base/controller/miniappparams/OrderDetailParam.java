package com.cmtt.base.controller.miniappparams;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cmtt.base.entity.SsOrderDetail;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/*********************************
 * @author andrew
 * @version v2.0
 *********************************/
@Data
public class OrderDetailParam {


    @ApiModelProperty(value = "订单编号")
    private String orderNumber;

    @ApiModelProperty(value = "商品ID")
    private Integer goodsId;

    @ApiModelProperty(value = "数量")
    private Integer num;

    @ApiModelProperty(value = "用户配送货地址")
    private Integer addressId;

    @ApiModelProperty(value = "用户水站id")
    private Integer stationId;


}
