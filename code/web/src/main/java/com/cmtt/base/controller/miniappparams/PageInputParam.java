package com.cmtt.base.controller.miniappparams;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;


/*********************************
* @author andrew
* @version v2.0
*********************************/
@Data
public class PageInputParam {


	@ApiModelProperty(value ="页数",example = "1")
	@NotNull(message = "当前页不能为空")
	private Integer pageNo;


	@ApiModelProperty(value ="页大小",example = "10")
	@NotNull(message = "页大小")
	private Integer pageSize;

}

