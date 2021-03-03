package com.cmtt.base.controller.miniappparams;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


/*********************************
* @author andrew
* @version v2.0
*********************************/
@Data
public class AddressInputParam {

	@ApiModelProperty(value = "序号")
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	@ApiModelProperty(value = "维度",example = "11")
	@NotNull(
			message = "维度不能为空"
	)
	private BigDecimal latitude;

	@ApiModelProperty(value = "经度",example = "111")
	@NotNull(
			message = "经度不能为空"
	)
	private BigDecimal longitude;

	@ApiModelProperty(value = "地址",example = "未来房子")
	private String address;

	@ApiModelProperty(value = "门牌号",example = "1单元1号")
	private String houseNumber;

	@ApiModelProperty(value = "联系人",example = "杨")
	private String contacts;

	@ApiModelProperty(value = "联系人手机号",example = "18286088391")
	private String phone;

	@ApiModelProperty(value = "性别 1-先生，2-女士",example = "1")
	private String sex;

	@ApiModelProperty(value = "标签 1-公司，2-家庭，3-店铺",example = "2")
	private String label;

	@ApiModelProperty(value = "是否默认地址 1-否，2-是",example = "1")
	private String isdefault;
}

