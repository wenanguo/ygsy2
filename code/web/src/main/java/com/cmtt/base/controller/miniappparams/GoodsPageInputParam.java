package com.cmtt.base.controller.miniappparams;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/*********************************
 * @author yuzm
 * @version v2.0
 *********************************/
@Data
public class GoodsPageInputParam extends PageInputParam{

    @ApiModelProperty(value ="结果类型，1-首页商品列表；2-水；3-水票",example = "1")
    @NotNull(message = "结果类型不能为空")
    private Integer type;

}
