package com.cmtt.base.controller.miniappparams;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/*********************************
 * @author andrew
 * @version v2.0
 *********************************/
@Data
public class GoodsInputParam{

    @ApiModelProperty(value = "商品编号",example = "35")
    @TableId(value = "goodsId", type = IdType.AUTO)
    private Integer goodsId;

    @ApiModelProperty(value ="结果类型，1-首页商品列表；2-水；3-水票",example = "1")
    private Integer type;

}
