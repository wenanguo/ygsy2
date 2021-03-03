package com.cmtt.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.time.LocalTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-02-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="SsClockinInfo对象", description="")
public class SsClockinInfo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "序号")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @ApiModelProperty(value = "用户打卡日期")
    private LocalDate clockinDate;

    @ApiModelProperty(value = "上班打卡时间")
    @JsonFormat(timezone = "GMT+8", pattern = "HH:mm:ss")
    private LocalTime inTime;

    @ApiModelProperty(value = "下班打卡时间")
    @JsonFormat(timezone = "GMT+8", pattern = "HH:mm:ss")
    private LocalTime outTime;


}
