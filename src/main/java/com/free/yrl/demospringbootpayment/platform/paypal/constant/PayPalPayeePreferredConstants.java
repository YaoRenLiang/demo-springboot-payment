package com.free.yrl.demospringbootpayment.platform.paypal.constant;

import lombok.Getter;

/**
 * PayPal商户首选的付款来源
 *
 * @author 姚壬亮
 **/
@Getter
public enum PayPalPayeePreferredConstants {

	UNRESTRICTED(1, "UNRESTRICTED", "接受来自客户的任何类型的付款。"),
	IMMEDIATE_PAYMENT_REQUIRED(2, "IMMEDIATE_PAYMENT_REQUIRED", "仅接受客户的即时付款。" +
			"例如，信用卡，PayPal余额或即时ACH。确保在捕获时，付款不具有“待处理”状态。");

	PayPalPayeePreferredConstants(Integer key, String value, String desc) {
		this.key = key;
		this.value = value;
		this.desc = desc;
	}

	private Integer key;
	private String value;
	private String desc;

}