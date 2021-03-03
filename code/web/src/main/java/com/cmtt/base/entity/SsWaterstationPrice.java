package com.cmtt.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since 2021-02-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="SsWaterstationPrice对象", description="")
public class SsWaterstationPrice extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "商品编号")
    @TableField("goodsId")
    private Integer goodsId;

    @ApiModelProperty(value = "水站id")
    @TableField("stationId")
    private Integer stationId;

    @ApiModelProperty(value = "水站进水价格")
    private Double price;


}
