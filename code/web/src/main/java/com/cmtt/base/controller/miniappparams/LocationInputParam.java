package com.cmtt.base.controller.miniappparams;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class LocationInputParam {

    @ApiModelProperty(value = "订单编号",example = "1613637417")
    private String orderNumber;
}


