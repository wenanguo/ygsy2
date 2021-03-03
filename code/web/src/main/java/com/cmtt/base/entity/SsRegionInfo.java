package com.cmtt.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * 区域信息表
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-02-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="SsRegionInfo对象", description="区域信息表")
public class SsRegionInfo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    @TableId(value = "area_id", type = IdType.AUTO)
    private Integer areaId;

    @ApiModelProperty(value = "区域编码")
    private String areacode;

    @ApiModelProperty(value = "区域名称")
    private String areadesc;

    @ApiModelProperty(value = "区域级别：1地市；2区县；3网格； 4乡镇 ；5村")
    private Integer areatype;

    @ApiModelProperty(value = "父节点")
    private String parentarea;

    @ApiModelProperty(value = "状态：0禁用；1启用")
    private Integer status;

    @ApiModelProperty(value = "短名称")
    private String shortName;

    private String regionCode;


}
