package com.free.yrl.demospringbootpayment.controller;

import com.free.yrl.demospringbootpayment.base.ResponseMessage;
import com.free.yrl.demospringbootpayment.constant.ResponseMessageConstants;
import com.free.yrl.demospringbootpayment.service.PaymentService;
import com.free.yrl.demospringbootpayment.service.StreamService;
import com.free.yrl.demospringbootpayment.util.UrlUtils;
import com.paypal.orders.Order;
import com.paypal.payments.Refund;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.free.yrl.demospringbootpayment.base.ResponseMessage.*;

/**
 * PayPal相关接口
 *
 * @author 姚壬亮
 **/
@Slf4j
@Api(tags = "PayPal相关接口")
@RestController
@RequestMapping("/paypal")
public class PayPalController {

	@Resource
	private PaymentService paymentServiceImpl;

	@Resource
	private StreamService streamServiceImpl;

	@GetMapping("cancel/{streamId}")
	public ResponseMessage<String> cancel(@PathVariable("streamId") Long streamId) {

		// 关闭当前订单
		Integer resultCode = streamServiceImpl.close(streamId);
		return controllerReturn(resultCode);

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
