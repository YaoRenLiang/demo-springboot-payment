package com.free.yrl.demospringbootpayment.platform.paypal.constant;

import lombok.Getter;

/**
 * PayPal客户结帐的着陆页类型
 *
 * @author 姚壬亮
 **/
@Getter
public enum PayPalLandingPageConstants {

	LOGIN(1, "LOGIN", "当客户单击PayPal Checkout时，" +
			"客户将被重定向到页面以登录PayPal并批准付款。"),
	BILLING(2, "BILLING", "当客户单击PayPal Checkout时，客户将被重定向到一个页面，" +
			"以输入信用卡或借记卡以及完成购买所需的其他相关账单信息。"),
	NO_PREFERENCE(3, "NO_PREFERENCE", "该值为默认值，当客户单击“ PayPal Checkout”时，" +
			"将根据其先前的交互，将其重定向到页面以登录PayPal并批准付款，" +
			"或重定向到页面以输入信用卡或借记卡以及完成购买所需的其他相关账单信息使用PayPal");

	private Integer key;
	private String value;
	private String desc;

	PayPalLandingPageConstants(Integer key, String value, String desc) {
		this.key = key;
		this.value = value;
		this.desc = desc;
	}

	public static String getValueByKey(Integer key) {
		for (PayPalLandingPageConstants object : PayPalLandingPageConstants.values()) {
			if (object.getKey().equals(key)) {
				return object.getValue();
			}
		}
		return "";
	}

}