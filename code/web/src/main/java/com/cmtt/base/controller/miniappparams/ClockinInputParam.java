package com.cmtt.base.controller.miniappparams;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/*********************************
 * @author yuzm
 * @version v2.0
 *********************************/
@Data
public class ClockinInputParam {

    @ApiModelProperty(value ="打卡方式，1-上班；2-下班",example = "1")
    private Integer flag;

    @ApiModelProperty(value ="按月份获取改月的打卡记录，为空表示获取当月打卡记录",example = "202102")
    private String month;

}
