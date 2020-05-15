package com.free.yrl.demospringbootpayment.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.free.yrl.demospringbootpayment.base.ResponseMessage;
import com.free.yrl.demospringbootpayment.constant.ResponseMessageConstants;
import com.free.yrl.demospringbootpayment.service.PaymentService;
import com.free.yrl.demospringbootpayment.service.StreamService;
import com.free.yrl.demospringbootpayment.service.impl.StreamServiceImpl;
import com.free.yrl.demospringbootpayment.util.UrlUtils;
import com.paypal.orders.Order;
import com.paypal.payments.Refund;
import com.sun.xml.internal.bind.v2.TODO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

	@Resource
	private StreamService streamServiceImpl;

	@ApiOperation("支付")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "platform", value = "付款类型：1.PayPal、2.Alipay、3.WeChat", dataType = "int", required = false),
	})
	@PostMapping(value = "pay")
	public ResponseMessage<String> pay(HttpServletRequest request,
									   @RequestBody List<String> myOrderIdList,
									   @RequestParam(required = false) Integer platform,
									   @RequestParam(required = false) String currencyCode) {

		String baseUrl = UrlUtils.getBaseUrl(request);
		return paymentServiceImpl.getPayInfo(myOrderIdList,
				platform,
				currencyCode,
				baseUrl + "/payment/cancel",
				baseUrl + "/payment/capture");

	}

	@GetMapping("cancel/{streamId}")
	public ResponseMessage<String> cancel(@PathVariable("streamId") Long streamId) {

		// 关闭当前订单
		Integer resultCode = streamServiceImpl.close(streamId);
		if (!resultCode.equals(ResponseMessageConstants.SUCCESSFULOPERATION.getKey())) {
			return error(resultCode);
		}
		return success();

	}

	@ApiOperation("此接口暴露给PayPal。用户授权付款之后，PayPal会回调该地址")
	@GetMapping("capture/{streamId}")
	public ResponseMessage<Order> capture(@PathVariable("streamId") Long streamId) {

		// TODO 更新订单状态，如果当前订单存在已经完结的流水，那么就是重复支付需触发全额退款操作。
		// TODO 把其余的订单支付方式给关闭掉
		return paymentServiceImpl.capture(streamId);

	}

	@ApiOperation("模拟退款")
	@GetMapping(value = "refund")
	public ResponseMessage<Refund> refund(@RequestParam(required = false) String myOrderId) {

		return paymentServiceImpl.refund(myOrderId);

	}

}
