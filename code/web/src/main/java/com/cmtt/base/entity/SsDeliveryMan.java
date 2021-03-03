package com.cmtt.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.cmtt.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;

/**
 * <p>
 * 配送员信息
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-01-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="SsDeliveryMan对象", description="配送员信息")
public class SsDeliveryMan extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "序号")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "配送员姓名")
    private String deliveryManName;

    @ApiModelProperty(value = "配送员类型 1-水站配送员，2-水厂配送员")
    private String deliveryManType;

    @ApiModelProperty(value = "所属水站Id")
    private Integer stationId;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "微信号")
    private String weixinNumber;

    @ApiModelProperty(value = "状态 101-禁用，100-正常")
    private Integer status;

    @ApiModelProperty(value = "创建人")
    private Integer createBy;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "最后一次更新人")
    private Integer lastUpdateBy;

    @ApiModelProperty(value = "最后一次更新时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastUpdateTime;


}
