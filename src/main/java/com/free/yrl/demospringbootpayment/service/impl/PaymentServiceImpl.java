package com.free.yrl.demospringbootpayment.service.impl;

import com.free.yrl.demospringbootpayment.base.ResponseMessage;
import com.free.yrl.demospringbootpayment.constant.ResponseMessageConstants;
import com.free.yrl.demospringbootpayment.entity.StreamEntity;
import com.free.yrl.demospringbootpayment.platform.paypal.constant.*;
import com.free.yrl.demospringbootpayment.platform.paypal.util.PayPalOrderUtils;
import com.free.yrl.demospringbootpayment.platform.paypal.util.PayPalPaymentUtils;
import com.free.yrl.demospringbootpayment.strategy.PaymentContext;
import com.free.yrl.demospringbootpayment.strategy.PaymentStrategy;
import com.free.yrl.demospringbootpayment.service.PaymentService;
import com.free.yrl.demospringbootpayment.service.StreamService;
import com.paypal.orders.Order;
import com.paypal.payments.Refund;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.free.yrl.demospringbootpayment.base.ResponseMessage.error;
import static com.free.yrl.demospringbootpayment.base.ResponseMessage.success;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

	@Resource
	private PayPalPaymentUtils payPalPaymentUtils;

	@Resource
	private StreamService streamServiceImpl;

	@Resource
	private PayPalOrderUtils payPalOrderUtils;

	@Resource
	private PaymentContext paymentContext;

	@Override
	public ResponseMessage<String> getPayInfo(List<String> myOrderIdList,
											  Integer platform,
											  String currencyCode,
											  String baseUrl) {

		String result = paymentContext.createOrder(myOrderIdList,
				platform,
				currencyCode,
				baseUrl);
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
		paymentContext.capture(streamEntity.getPlatform(),
				streamEntity.getPlatformOrderId());
		return success();

	}

	@Override
	public ResponseMessage<Refund> refund(String myOrderId) {

		/*TODO 此处应该拿着本平台的订单Id，查询到对应的PayPalCaptureId，然后进行退款操作。
		 * 因为此demo不涉及数据库，所以这边省略，订单捕获成功之后，需手动去支付的日志里拿到PayPalCaptureId
		 * 然后触发捕获退款refund()接口完成退款操作*/
		String captureId = myOrderId;
		Refund refund = payPalPaymentUtils.refund(captureId, PayPalCurrencyConstants.USD, "1");
		return success(refund);

	}

}
