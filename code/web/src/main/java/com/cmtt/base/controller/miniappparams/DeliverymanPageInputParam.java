package com.cmtt.base.controller.miniappparams;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/*********************************
 * @author yuzm
 * @version v2.0
 *********************************/
@Data
public class DeliverymanPageInputParam extends PageInputParam{

    @ApiModelProperty(value ="水站ID", example = "1")
    @NotNull(message = "水站ID不能为空")
    private Integer stationId;

}
