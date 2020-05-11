package com.free.yrl.demospringbootpayment.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.free.yrl.demospringbootpayment.base.ResponseMessage;
import com.free.yrl.demospringbootpayment.service.PaymentService;
import com.free.yrl.demospringbootpayment.util.UrlUtils;
import com.paypal.orders.Order;
import com.paypal.payments.Refund;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static com.free.yrl.demospringbootpayment.base.ResponseMessage.error;
import static com.free.yrl.demospringbootpayment.base.ResponseMessage.success;

/**
 * 付款相关接口
 *
 * @author 姚壬亮
 **/
@Slf4j
@Api(tags = "付款相关接口")
@RestController
@RequestMapping("/payment")
public class PaymentController {

	@Resource
	private PaymentService paymentServiceImpl;

	@ApiOperation("支付")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "type", value = "付款类型：1.PayPal、2.Alipay、3.WeChat", dataType = "int", required = false),
	})
	@PostMapping(value = "pay")
	public ResponseMessage<String> pay(HttpServletRequest request,
									   @RequestParam(required = false) Integer type,
									   @RequestParam(required = false) String myOrderId) {

		String cancelUrl = UrlUtils.getBaseUrl(request) + "/payment/cancel" + "/" + myOrderId;
		String captureUrl = UrlUtils.getBaseUrl(request) + "/payment/capture" + "/" + myOrderId;
		String payInfo = paymentServiceImpl.getPayInfo(type,
				myOrderId,
				cancelUrl,
				captureUrl);
		return success(payInfo);

	}

	@GetMapping("cancel/{myOrderId}")
	public ResponseMessage<String> cancel(@PathVariable("myOrderId") String myOrderId) {

		return success("本平台的订单Id" + myOrderId);

	}

	@ApiOperation("此接口暴露给PayPal。用户授权付款之后，PayPal会回调该地址")
	@GetMapping("capture/{myOrderId}")
	public ResponseMessage<String> capture(@PathVariable("myOrderId") String myOrderId) {

		Order order = paymentServiceImpl.capture(myOrderId);
		return success("本平台的订单Id" + myOrderId + "，扣款成功后的订单信息" + order);

	}

	@ApiOperation("模拟退款")
	@GetMapping(value = "refund")
	public ResponseMessage<String> refund(@RequestParam(required = false) String myOrderId) {

		Refund refund = paymentServiceImpl.refund(myOrderId);
		return success("退款信息" + refund);

	}

}
