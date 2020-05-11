package com.free.yrl.demospringbootpayment.service.impl;

import com.free.yrl.demospringbootpayment.constant.PaymentTypeConstants;
import com.free.yrl.demospringbootpayment.platform.paypal.condition.ItemCondition;
import com.free.yrl.demospringbootpayment.platform.paypal.condition.PayPalCondition;
import com.free.yrl.demospringbootpayment.platform.paypal.condition.PayPalDetailedCondition;
import com.free.yrl.demospringbootpayment.platform.paypal.constant.*;
import com.free.yrl.demospringbootpayment.platform.paypal.util.PayPalOrderUtils;
import com.free.yrl.demospringbootpayment.platform.paypal.util.PayPalPaymentUtils;
import com.free.yrl.demospringbootpayment.service.PaymentService;
import com.google.common.collect.Lists;
import com.paypal.orders.LinkDescription;
import com.paypal.orders.Order;
import com.paypal.payments.Refund;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 支付服务
 *
 * @author 姚壬亮
 **/
@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

	/**
	 * 本平台的订单Id
	 */
	private String myOrderId;

	/**
	 * 客户取消付款后，将客户重定向到的URL
	 */
	private String cancelUrl;

	/**
	 * 客户付款成功后，将客户重定向到的URL
	 */
	private String returnUrl;

	@Resource
	private PayPalOrderUtils payPalOrderUtils;

	@Resource
	private PayPalPaymentUtils payPalPaymentUtils;

	@Override
	public String getPayInfo(Integer type,
							 String myOrderId,
							 String cancelUrl,
							 String returnUrl) {

		this.myOrderId = myOrderId;
		this.cancelUrl = cancelUrl;
		this.returnUrl = returnUrl;
		String result = null;
		// 在PayPal网站上显示以供客户结帐的着陆页类型，详情参考枚举 PayPalLandingPageConstants
		Integer landingPage = PayPalLandingPageConstants.NO_PREFERENCE.getKey();
		if (type.equals(PaymentTypeConstants.PAYPAL.getKey())) {
			// PayPal支付
			Order order = payPalOrderUtils.createOrder(getPayPalCondition(landingPage));
			String payPalOrderId = order.id();
			log.info("PayPal Order ID: " + payPalOrderId);
			String payLink = null;
			order.links().forEach(link -> System.out.println(link.rel() + " => " + link.method() + ":" + link.href()));
			List<LinkDescription> linkList = order.links();
			for (LinkDescription link : linkList) {
				if ("approve".equalsIgnoreCase(link.rel())) {
					// 将用户重定向到批准付款链接
					payLink = link.href();
				}
			}
			result = payLink;
		} else if (type.equals(PaymentTypeConstants.ALIPAY.getKey())) {
			// Alipay支付

		} else if (type.equals(PaymentTypeConstants.WECHAT.getKey())) {
			// WeChat支付

		} else {
			return null;
		}
		/*记录付款相关信息*/
		return result;

	}

	@Override
	public Order capture(String myOrderId) {

		/*此处应该拿着本平台的订单Id，查询到对应的PayPalOrderId，然后进行捕获操作。
		 * 因为此demo不涉及数据库，所以这边省略，订单创建成功之后，需手动去支付的日志里拿到PayPalOrderId
		 * 然后触发捕获扣款deduct()接口完成扣款操作*/
		String payPalOrderId = myOrderId;
		return payPalOrderUtils.capture(payPalOrderId);

	}

	@Override
	public Refund refund(String myOrderId) {

		/*此处应该拿着本平台的订单Id，查询到对应的PayPalCaptureId，然后进行退款操作。
		 * 因为此demo不涉及数据库，所以这边省略，订单捕获成功之后，需手动去支付的日志里拿到PayPalCaptureId
		 * 然后触发捕获退款refund()接口完成退款操作*/
		String captureId = myOrderId;
		return payPalPaymentUtils.refund(captureId, PayPalCurrencyConstants.USD, "1");

	}

	/**
	 * 获取PayPal入参
	 *
	 * @param landingPage PayPal客户结帐的着陆页类型
	 * @return PayPal入参
	 */
	private PayPalCondition getPayPalCondition(Integer landingPage) {

		List<PayPalDetailedCondition> payPalDetailedConditionList = getPayPalDetailedConditionList(myOrderId);
		return PayPalCondition.builder()
				.paymentIntent(PayPalPaymentIntentConstants.CAPTURE.getValue())
				.cancelUrl(cancelUrl)
				.returnUrl(returnUrl)
				.landingPage(PayPalLandingPageConstants.getValueByKey(landingPage))
				.payeePreferred(PayPalPayeePreferredConstants.UNRESTRICTED.getValue())
				.userAction(PayPalUserActionConstants.PAY_NOW.getValue())
				// 付款人邮箱
				.email("123123@foxmail.com")
				.payPalDetailedConditionList(payPalDetailedConditionList)
				.build();

	}

	/**
	 * list里有几个entity将会在PayPal对应生成几个订单
	 *
	 * @param myOrderId 本平台的myOrderId
	 * @return 商品信息
	 */
	private List<PayPalDetailedCondition> getPayPalDetailedConditionList(String myOrderId) {

		List<PayPalDetailedCondition> payPalDetailedConditionList = Lists.newArrayList();
		List<ItemCondition> itemConditionList = getItemConditionList();
		for (int i = 0; i < 3; i++) {
			PayPalDetailedCondition payPalDetailedCondition = PayPalDetailedCondition.builder()
					.myOrderId(myOrderId + i)
					.currencyCode(PayPalCurrencyConstants.USD)
					.itemConditionList(itemConditionList)
					.discount("0.1")
					.handling("0.2")
					.insurance("0.3")
					.shipping("0.4")
					.shippingDiscount("0.2")
					.description("这是最大的购买说明" + i)
					.build();
			payPalDetailedConditionList.add(payPalDetailedCondition);
		}
		return payPalDetailedConditionList;

	}

	private List<ItemCondition> getItemConditionList() {

		List<ItemCondition> itemConditionList = Lists.newArrayList();
		for (int i = 1; i < 3; i++) {
			ItemCondition itemCondition = ItemCondition.builder()
					.name("item标题" + i)
					.quantity(i + "")
					.unitAmount(i + "")
					.tax("0.03")
					.sku("大个")
					.description("item说明" + i)
					.build();
			itemConditionList.add(itemCondition);
		}
		return itemConditionList;

	}

}
