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
				baseUrl);

	}

}
