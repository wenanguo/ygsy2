package com.cmtt.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.cmtt.base.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 客户信息
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-01-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="SsCustomerInfo对象", description="客户信息")
public class SsCustomerInfo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "序号")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "客户ID")
    private String customerId;

    @ApiModelProperty(value = "客户名称")
    @TableField(condition = SqlCondition.LIKE)
    private String customerName;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "客户头像")
    private String customerImg;

    @ApiModelProperty(value = "性别 0-未知，1-男，2-女")
    private String sex;

    @ApiModelProperty(value = "OPENID")
    private String openid;

    @ApiModelProperty(value = "备注")
    private String memo;

    @ApiModelProperty(value = "状态 1-正常，2-注销")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;


   /* @ApiModelProperty(value = "订单总数")
    private String orderTotal;

    @ApiModelProperty(value = "水票总数")
    private String ticketTotal;

    @ApiModelProperty(value = "总金额")
    private String moneyTotal;*/
}
