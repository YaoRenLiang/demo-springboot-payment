package com.free.yrl.demospringbootpayment.platform.paypal.condition;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * PayPal入参
 *
 * @author 姚壬亮
 **/
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "PayPal入参")
public class PayPalCondition implements Serializable {

	@ApiModelProperty(value = "支付意图，详情参考枚举 PayPalPaymentIntentConstants", required = true)
	private String paymentIntent;

	@ApiModelProperty(value = "客户取消付款后，将客户重定向到的URL", required = true)
	private String cancelUrl;

	@ApiModelProperty(value = "客户付款成功后，将客户重定向到的URL", required = true)
	private String returnUrl;

	@ApiModelProperty(value = "在PayPal网站上显示以供客户结帐的着陆页类型，详情参考枚举 PayPalLandingPageConstants", required = true)
	private String landingPage;

	@ApiModelProperty(value = "商户首选的付款来源，详情参考枚举 PayPalPayeePreferredConstants", required = true)
	private String payeePreferred;

	@ApiModelProperty(value = "配置继续或立即付款结帐流程，详情参考枚举 PayPalUserActionConstants", required = true)
	private String userAction;

	@ApiModelProperty(value = "付款人邮箱", required = true)
	private String email;

	@ApiModelProperty(value = "PayPal购买详情入参", required = true)
	private List<PayPalDetailedCondition> payPalDetailedConditionList;

	@ApiModelProperty(value = "运送偏好，详情参考枚举 PayPalPayeePreferredConstants", required = false)
	private String shippingPreference;

}
