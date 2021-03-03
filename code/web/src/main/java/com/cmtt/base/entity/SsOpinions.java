package com.cmtt.base.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * 
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-02-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="SsOpinions对象", description="")
public class SsOpinions extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "意见ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "意见内容")
    @TableField("content")
    private String content;

    @ApiModelProperty(value = "意见回复父id")
    @TableField("parentId")
    private Integer parentId;

    @ApiModelProperty(value = "商品id")
    @TableField("goodsId")
    private String goodsId;

    @ApiModelProperty(value = "用户id")
    @TableField("customerId")
    private String customerId;

    @ApiModelProperty(value = "回复人id")
    @TableField("replayId")
    private String replayId;

    @ApiModelProperty(value = "1-普通意见回复，2-商品评价")
    @TableField("opinion_type")
    private String opinionType;

    @ApiModelProperty(value = "回复状态:1-已回复；2-未回复")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "意见回复时间")
    @TableField("createTime")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "意见回馈内容")
    @TableField(exist = false)
    private String replyContent;

    @ApiModelProperty(value = "意见回馈时间")
    @TableField(exist = false)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime replyTime;

    @ApiModelProperty(value = "用户电话")
    @TableField(exist = false)
    private String phone;

    @ApiModelProperty(value = "用户电话")
    @TableField(exist = false)
    private String username;


}
