package com.free.yrl.demospringbootpayment.service;

import com.free.yrl.demospringbootpayment.base.ResponseMessage;
import com.paypal.orders.Order;
import com.paypal.payments.Refund;

import java.util.Collection;
import java.util.List;

/**
 * PayPal服务
 *
 * @author 姚壬亮
 **/
public interface PaymentService {

	/**
	 * 获取授权支付信息
	 *
	 * @param myOrderIdList 一组本平台的orderId
	 * @param platform      支付平台，详情参考枚举 PlatformConstants
	 * @param currencyCode  币种，详情参考枚举 PayPalCurrencyConstants
	 * @param cancelUrl     客户取消付款后，将客户重定向到的URL
	 * @param captureUrl    客户付款成功后，将客户重定向到的URL
	 * @return 授权支付链接
	 */
	ResponseMessage<String> getPayInfo(List<String> myOrderIdList,
					  Integer platform,
					  String currencyCode,
					  String cancelUrl,
					  String captureUrl);

	/**
	 * 捕获订单
	 *
	 * @param streamId 流水单表主键
	 * @return 订单信息
	 */
	ResponseMessage<Order> capture(Long streamId);

	/**
	 * 退款
	 *
	 * @param myOrderId 本平台的orderId
	 * @return 退款信息
	 */
	ResponseMessage<Refund> refund(String myOrderId);

}
