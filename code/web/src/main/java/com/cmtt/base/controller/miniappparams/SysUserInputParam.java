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
public class SysUserInputParam {

    @ApiModelProperty(value ="用户ID", example = "1")
    private Integer id;

    @ApiModelProperty(value ="水站ID", example = "1")
    @NotNull(message = "水站ID不能为空")
    private Integer stationId;

    @ApiModelProperty(value = "配送员姓名", example = "张三")
    @NotNull(message = "配送员姓名不能为空")
    private String nickname;

    @ApiModelProperty(value = "手机号", example = "13800001111")
    private String phone;

    @ApiModelProperty(value = "性别", example = "1")
    private Integer sex;

    @ApiModelProperty(value = "状态 101-禁用，100-正常", example = "100")
    private Integer status;

    @ApiModelProperty(value = "头像", example = "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3480070665,2380656812&fm=26&gp=0.jpg")
    private String icon;

}
