package com.free.yrl.demospringbootpayment.service.impl;

import com.free.yrl.demospringbootpayment.base.ResponseMessage;
import com.free.yrl.demospringbootpayment.base.SnowFlake;
import com.free.yrl.demospringbootpayment.constant.PlatformConstants;
import com.free.yrl.demospringbootpayment.constant.ResponseMessageConstants;
import com.free.yrl.demospringbootpayment.dao.DetailDao;
import com.free.yrl.demospringbootpayment.dao.StreamDao;
import com.free.yrl.demospringbootpayment.entity.StreamEntity;
import com.free.yrl.demospringbootpayment.platform.paypal.condition.ItemCondition;
import com.free.yrl.demospringbootpayment.platform.paypal.condition.PayPalCondition;
import com.free.yrl.demospringbootpayment.platform.paypal.condition.PayPalDetailedCondition;
import com.free.yrl.demospringbootpayment.platform.paypal.constant.*;
import com.free.yrl.demospringbootpayment.platform.paypal.util.PayPalOrderUtils;
import com.free.yrl.demospringbootpayment.platform.paypal.util.PayPalPaymentUtils;
import com.free.yrl.demospringbootpayment.service.PaymentService;
import com.free.yrl.demospringbootpayment.service.StreamService;
import com.google.common.collect.Lists;
import com.paypal.orders.LinkDescription;
import com.paypal.orders.Order;
import com.paypal.payments.Refund;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

import static com.free.yrl.demospringbootpayment.base.ResponseMessage.error;
import static com.free.yrl.demospringbootpayment.base.ResponseMessage.success;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

	@Resource
	private SnowFlake snowFlake;

	@Resource
	private StreamService streamServiceImpl;

	@Resource
	private PayPalOrderUtils payPalOrderUtils;

	@Resource
	private PayPalPaymentUtils payPalPaymentUtils;

	@Override
	public ResponseMessage<String> getPayInfo(List<String> myOrderIdList,
											  Integer platform,
											  String currencyCode,
											  String baseUrl) {

		String result = null;
		String platformOrderId = null;
		// 记录付款相关信息，创建订单流水
		Long id = snowFlake.nextId();
		Integer resultCode = streamServiceImpl.insert(StreamEntity.builder()
				.id(id)
				.sn(snowFlake.nextId() + "")
				.platform(platform)
				.currencyCode(currencyCode)
				// TODO 订单总价
				.amount(new BigDecimal("998"))
				.status(1)
				.build());
		if (!resultCode.equals(ResponseMessageConstants.SUCCESS_OPERATION.getKey())) {
			return error(resultCode);
		}
		if (platform.equals(PlatformConstants.PAYPAL.getKey())) {
			// PayPal支付
			Order order = payPalOrderUtils.createOrder(id,
					myOrderIdList,
					currencyCode,
					baseUrl + "/paypal/cancel/" + id,
					baseUrl + "/paypal/capture/" + id);
			platformOrderId = order.id();
			order.links().forEach(link -> System.out.println(link.rel() + " => " + link.method() + ":" + link.href()));
			List<LinkDescription> linkList = order.links();
			for (LinkDescription link : linkList) {
				if ("approve".equalsIgnoreCase(link.rel())) {
					// 将用户重定向到批准付款链接
					result = link.href();
				}
			}
		} else if (platform.equals(PlatformConstants.ALIPAY.getKey())) {
			// Alipay支付

		} else if (platform.equals(PlatformConstants.WECHAT.getKey())) {
			// WeChat支付

		} else {
			// 没有匹配就返回null
			return error(ResponseMessageConstants.SERVICE_EXCEPTION);
		}
		log.info("Platform Order ID: " + platformOrderId);
		// 记录付款相关信息，创建订单流水
		resultCode = streamServiceImpl.updateById(StreamEntity.builder()
				.id(id)
				.platformOrderId(platformOrderId)
				.build());
		if (!resultCode.equals(ResponseMessageConstants.SUCCESS_OPERATION.getKey())) {
			return error(resultCode);
		}
		return success(result);

	}

	@Override
	public ResponseMessage<Order> capture(Long streamId) {

		/*此处应该拿着本平台的订单Id，查询到对应的PayPalOrderId，然后进行捕获操作。
		 * 然后触发捕获扣款deduct()接口完成扣款操作*/
		ResponseMessage<StreamEntity> result = streamServiceImpl.selectById(streamId);
		if (!result.getCode().equals(ResponseMessageConstants.SUCCESS_OPERATION.getKey())) {
			return error(result.getCode());
		}
		StreamEntity streamEntity = result.getData();
		Order order = payPalOrderUtils.capture(streamEntity.getPlatformOrderId());
		return success(order);

	}

	@Override
	public ResponseMessage<Refund> refund(String myOrderId) {

		/*此处应该拿着本平台的订单Id，查询到对应的PayPalCaptureId，然后进行退款操作。
		 * 因为此demo不涉及数据库，所以这边省略，订单捕获成功之后，需手动去支付的日志里拿到PayPalCaptureId
		 * 然后触发捕获退款refund()接口完成退款操作*/
		String captureId = myOrderId;
		Refund refund = payPalPaymentUtils.refund(captureId, PayPalCurrencyConstants.USD, "1");
		return success(refund);

	}

}
