package com.free.yrl.demospringbootpayment.platform.paypal.constant;

import lombok.Getter;

/**
 * PayPal运送偏好：
 * 向客户显示送货地址。
 * 使客户能够选择PayPal网站上的地址。
 * 限制客户在付款批准过程中更改地址。
 *
 * @author 姚壬亮
 **/
@Getter
public enum PayPalShippingPreferenceConstants {

	GET_FROM_FILE(1, "GET_FROM_FILE", "该值为默认值，使用PayPal网站上客户提供的送货地址"),
	NO_SHIPPING(2, "NO_SHIPPING", "从PayPal网站编辑送货地址。推荐用于数字商品"),
	SET_PROVIDED_ADDRESS(3, "SET_PROVIDED_ADDRESS", "使用商家提供的地址," +
			"客户无法在PayPal网站上更改此地址");

	PayPalShippingPreferenceConstants(Integer key, String value, String desc) {
		this.key = key;
		this.value = value;
		this.desc = desc;
	}

	private Integer key;
	private String value;
	private String desc;

}