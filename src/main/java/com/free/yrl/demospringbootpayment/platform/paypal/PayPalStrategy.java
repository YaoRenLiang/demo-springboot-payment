package com.free.yrl.demospringbootpayment.platform.paypal;

import com.free.yrl.demospringbootpayment.constant.ResponseMessageConstants;
import com.free.yrl.demospringbootpayment.platform.paypal.util.PayPalOrderUtils;
import com.free.yrl.demospringbootpayment.platform.paypal.util.PayPalPaymentUtils;
import com.free.yrl.demospringbootpayment.strategy.PaymentStrategy;
import com.paypal.orders.LinkDescription;
import com.paypal.orders.Order;

import javax.annotation.Resource;
import java.util.List;

/**
 * PayPal
 *
 * @author 姚壬亮
 **/
public class PayPalStrategy extends PaymentStrategy {

	@Resource
	private PayPalOrderUtils payPalOrderUtils;

	@Resource
	private PayPalPaymentUtils payPalPaymentUtils;

	@Override
	public String createOrder(List<String> myOrderIdList,
							  Integer platform,
							  String currencyCode,
							  String baseUrl) {

		String result = null;
		Long id = super.insertStream(platform, currencyCode);
		if (id == null) {
			return result;
		}
		Order order = payPalOrderUtils.createOrder(id,
				myOrderIdList,
				currencyCode,
				baseUrl + "/paypal/cancel/" + id,
				baseUrl + "/paypal/capture/" + id);
		String platformOrderId = order.id();
		order.links().forEach(link -> System.out.println(link.rel() + " => " + link.method() + ":" + link.href()));
		List<LinkDescription> linkList = order.links();
		for (LinkDescription link : linkList) {
			if ("approve".equalsIgnoreCase(link.rel())) {
				// 将用户重定向到批准付款链接
				result = link.href();
			}
		}
		Integer resultCode = super.updateStreamById(id, platformOrderId);
		if (!resultCode.equals(ResponseMessageConstants.SUCCESS_OPERATION.getKey())) {
			result = null;
		}
		return result;

	}

	@Override
	public void capture(String payPalOrderId) {

		payPalOrderUtils.capture(payPalOrderId);

	}

	@Override
	public void refund(String captureId,
					   String currencyCode,
					   String amount) {

		payPalPaymentUtils.refund(captureId,
				currencyCode,
				amount);

	}

}
