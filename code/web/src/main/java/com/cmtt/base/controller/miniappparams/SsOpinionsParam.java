package com.cmtt.base.controller.miniappparams;

import com.baomidou.mybatisplus.annotation.TableField;
import com.cmtt.base.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-02-16
 */
@Data
public class SsOpinionsParam {


    @ApiModelProperty(value = "意见内容")
    @TableField("content")
    private String content;

//
//    @ApiModelProperty(value = "商品id")
//    @TableField("goodsId")
//    private String goodsId;

    @ApiModelProperty(value = "1-普通意见回复，2-商品评价")
    @TableField("opinion_type")
    private String opinionType;



}
