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
 * banner信息
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-01-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="SsBannerInfo对象", description="banner信息")
public class SsBannerInfo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "序号")
    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "banner名称")
    private String bannerName;

    @ApiModelProperty(value = "Banner类型，1-外链，2-商品，3-富文本")
    private Integer bannerType;

    @ApiModelProperty(value = "banner图片")
    private String bannerImg;

    @ApiModelProperty(value = "banner内容")
    private String bannerContent;

    @ApiModelProperty(value = "商品Id")
    private Integer goodsId;

    @ApiModelProperty(value = "排序号")
    private Integer sort;

    @ApiModelProperty(value = "状态 101-禁用，100-启用")
    private Integer status;

    @ApiModelProperty(value = "失效时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expiredTime;

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

    @ApiModelProperty(value = "使用标识，1-用户端，2-水站端")
    private Integer useFlag;

    @ApiModelProperty(value = "外链地址")
    private String linkUrl;
}
