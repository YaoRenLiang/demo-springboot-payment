package com.free.yrl.demospringbootpayment.constant;

import lombok.Getter;

/**
 * 付款类型
 *
 * @author 姚壬亮
 **/
@Getter
public enum PaymentTypeConstants {

	PAYPAL(1, "PayPal"),
	ALIPAY(2, "Alipay"),
	WECHAT(3, "WeChat");

	PaymentTypeConstants(Integer key, String value) {
		this.key = key;
		this.value = value;
	}

	private Integer key;
	private String value;

}