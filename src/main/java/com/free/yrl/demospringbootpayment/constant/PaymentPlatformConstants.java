package com.free.yrl.demospringbootpayment.constant;

import lombok.Getter;

/**
 * 付款类型
 *
 * @author 姚壬亮
 **/
@Getter
public enum PaymentPlatformConstants {

	PAYPAL(1, "PayPal"),
	ALIPAY(2, "Alipay"),
	WECHAT(3, "WeChat"),
	GMO(4, "GMO");

	PaymentPlatformConstants(Integer key, String value) {
		this.key = key;
		this.value = value;
	}

	private Integer key;
	private String value;

}