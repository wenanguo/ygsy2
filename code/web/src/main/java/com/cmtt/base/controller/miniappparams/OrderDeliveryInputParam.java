package com.cmtt.base.controller.miniappparams;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/*********************************
 * @author yuzm
 * @version v2.0
 *********************************/
@Data
public class OrderDeliveryInputParam {

    @ApiModelProperty(value = "订单编号", example = "1613307751")
    @NotNull(message = "订单编号不能为空")
    private String orderNumber;

}
