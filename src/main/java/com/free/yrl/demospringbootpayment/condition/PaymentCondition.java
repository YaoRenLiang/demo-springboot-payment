package com.free.yrl.demospringbootpayment.condition;

import com.free.yrl.demospringbootpayment.platform.paypal.condition.PayPalCondition;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 付款入参
 *
 * @author 姚壬亮
 **/
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "付款入参")
public class PaymentCondition implements Serializable {

	@ApiModelProperty(value = "PayPal入参", required = true)
	private PayPalCondition payPalCondition;

}
