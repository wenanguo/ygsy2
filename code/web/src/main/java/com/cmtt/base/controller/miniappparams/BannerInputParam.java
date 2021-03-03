package com.cmtt.base.controller.miniappparams;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/*********************************
 * @author yuzm
 * @version v2.0
 *********************************/
@Data
public class BannerInputParam {

    @ApiModelProperty(value ="使用标识，1-用户端，2-水站端",example = "1")
    @NotNull(message = "使用标识不能为空")
    private Integer useFlag;

}
