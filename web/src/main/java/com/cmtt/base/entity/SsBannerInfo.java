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

    @ApiModelProperty(value = "banner图片")
    private String bannerImg;

    @ApiModelProperty(value = "banner内容")
    private String bannerContent;

    @ApiModelProperty(value = "商品编码")
    private String goodsCode;

    @ApiModelProperty(value = "排序号")
    private Integer sort;

    @ApiModelProperty(value = "状态 0-禁用，1-启用")
    private String status;

    @ApiModelProperty(value = "失效时间")
    private LocalDateTime expiredTime;

    @ApiModelProperty(value = "创建人")
    private Integer createBy;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "最后一次更新人")
    private Integer lastUpdateBy;

    @ApiModelProperty(value = "最后一次更新时间")
    private LocalDateTime lastUpdateTime;


}
