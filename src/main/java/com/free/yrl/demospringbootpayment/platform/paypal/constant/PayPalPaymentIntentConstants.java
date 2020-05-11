package com.free.yrl.demospringbootpayment.platform.paypal.constant;

import lombok.Getter;

/**
 * PayPal支付意图
 *
 * @author 姚壬亮
 **/
@Getter
public enum PayPalPaymentIntentConstants {

	CAPTURE(1, "CAPTURE", "商家打算在顾客付款后立即付款"),
	AUTHORIZE(2, "AUTHORIZE", "授权稍后支付费用，之后必须单独提出付款请求，以获取按需付款" +
			"商家打算授权付款，并在客户付款后将资金置于保留状态。授权付款最多可保证三天，但最多可收取29天。" +
			"三天的荣誉期后，原始的授权付款将过期，您必须重新授权该付款。您必须另外发出请求以捕获按需付款。" +
			"如果您的订单中有多个`purchase_unit`，则不支持此意图。");

	PayPalPaymentIntentConstants(Integer key, String value, String desc) {
		this.key = key;
		this.value = value;
		this.desc = desc;
	}

	private Integer key;
	private String value;
	private String desc;

}