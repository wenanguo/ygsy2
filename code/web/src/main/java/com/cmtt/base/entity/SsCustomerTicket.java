package com.cmtt.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * 用户购买水票信息
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-01-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="SsCustomerTicket对象", description="用户购买水票信息")
public class SsCustomerTicket extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "序号")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "客户ID")
    private String customerId;

    @ApiModelProperty(value = "水票编码")
    private String ticketCode;

    @ApiModelProperty(value = "水票ID")
    private Integer ticketId;

    @ApiModelProperty(value = "使用状态 0-未使用，1-已使用")
    private Integer useStatus;

    @ApiModelProperty(value = "使用时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime useTime;

    @ApiModelProperty(value = "使用时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @TableField(exist = false,condition = SqlCondition.LIKE)
    private String ticketName;

    @TableField(exist = false)
    private String ticketType;

    @TableField(exist = false)
    private String ticketImg;

    //水票总数
    @TableField(exist = false)
    private Integer ticketTotal;
}
