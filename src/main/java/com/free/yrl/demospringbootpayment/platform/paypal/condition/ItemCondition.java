package com.free.yrl.demospringbootpayment.platform.paypal.condition;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 规格入参
 *
 * @author 姚壬亮
 **/
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "规格入参")
public class ItemCondition implements Serializable {

	@ApiModelProperty(value = "项目名称或标题", required = true)
	private String name;

	@ApiModelProperty(value = "项目数量，必须是整数", required = true)
	private String quantity;

	@ApiModelProperty(value = "单个商品的价格", required = true)
	private String unitAmount;

	@ApiModelProperty(value = "单个商品的税", required = false)
	private String tax;

	@ApiModelProperty(value = "规格单位。例子：个、只、筐", required = false)
	private String sku;

	@ApiModelProperty(value = "详细项目说明", required = false)
	private String description;

}
