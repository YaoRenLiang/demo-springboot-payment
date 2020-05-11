package com.free.yrl.demospringbootpayment.service;

import com.paypal.orders.Order;
import com.paypal.payments.Refund;

/**
 * PayPal服务
 *
 * @author 姚壬亮
 **/
public interface PaymentService {

	/**
	 * 获取授权支付信息
	 *
	 * @param type      付款类型，详情参考枚举 PaymentTypeConstants
	 * @param myOrderId 本平台的orderId
	 * @param cancelUrl 客户取消付款后，将客户重定向到的URL
	 * @param returnUrl 客户付款成功后，将客户重定向到的URL
	 * @return 授权支付链接
	 */
	String getPayInfo(Integer type,
					  String myOrderId,
					  String cancelUrl,
					  String returnUrl);

	/**
	 * 捕获订单
	 *
	 * @param myOrderId 本平台的orderId
	 * @return 订单信息
	 */
	Order capture(String myOrderId);

	/**
	 * 退款
	 *
	 * @param myOrderId 本平台的orderId
	 * @return 退款信息
	 */
	Refund refund(String myOrderId);

}
