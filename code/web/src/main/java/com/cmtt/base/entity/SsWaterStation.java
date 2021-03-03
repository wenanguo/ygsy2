package com.cmtt.base.entity;

import java.math.BigDecimal;
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
 * 水站信息表
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-01-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="SsWaterStation对象", description="水站信息表")
public class SsWaterStation extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "序号")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "水站名称")
    private String stationName;

    @ApiModelProperty(value = "负责人姓名")
    private String leaderName;

    @ApiModelProperty(value = "负责人手机号")
    private String phone;

    @ApiModelProperty(value = "OpenId")
    private String openId;

    @ApiModelProperty(value = "座机")
    private String landline;

    @ApiModelProperty(value = "水站所在省份")
    private String province;

    @ApiModelProperty(value = "水站所在地市")
    private String city;

    @ApiModelProperty(value = "水站所在区县")
    private String county;

    @ApiModelProperty(value = "水站所在乡镇")
    private String town;

    @ApiModelProperty(value = "水站详细地址")
    private String address;

    @ApiModelProperty(value = "水站经度")
    private BigDecimal latitude;

    @ApiModelProperty(value = "水站维度")
    private BigDecimal longitude;

    @ApiModelProperty(value = "水站营业状态 101-未营业，100-营业中")
    private Integer status;

    @ApiModelProperty(value = "可用余额")
    private BigDecimal availableBalance;

    @ApiModelProperty(value = "冻结余额")
    private BigDecimal frozenBalance;

    @ApiModelProperty(value = "创建人")
    private Integer createBy;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "最后更新人")
    private Integer lastUpadteBy;

    @ApiModelProperty(value = "最后更新时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastUpdateTime;


    @ApiModelProperty(value = "水站所在省份名称")
    private String provinceName;

    @ApiModelProperty(value = "水站所在地市名称")
    private String cityName;

    @ApiModelProperty(value = "水站所在区县名称")
    private String countyName;

    @ApiModelProperty(value = "水站所在乡镇名称")
    private String townName;

}
