package com.free.yrl.demospringbootpayment.platform.paypal.constant;

import lombok.Getter;

/**
 * PayPal配置继续或立即付款结帐流程
 *
 * @author 姚壬亮
 **/
@Getter
public enum PayPalUserActionConstants {

	CONTINUE(1, "CONTINUE", "将客户重定向到PayPal付款页面后，将出现“ 继续”按钮。" +
			"当结帐流程启动时最终金额未知时，请使用此选项，并且您想将客户重定向到商家页面而不处理付款。"),
	PAY_NOW(2, "PAY_NOW", "将客户重定向到PayPal付款页面后，出现“ 立即付款”按钮。" +
			"当启动结帐时知道最终金额并且您要在客户单击“ 立即付款”时立即处理付款时，请使用此选项。");

	PayPalUserActionConstants(Integer key, String value, String desc) {
		this.key = key;
		this.value = value;
		this.desc = desc;
	}

	private Integer key;
	private String value;
	private String desc;

}