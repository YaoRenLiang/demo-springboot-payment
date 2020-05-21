package com.free.yrl.demospringbootpayment.strategy;

import com.google.common.collect.Maps;
import lombok.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 策略执行
 *
 * @author 姚壬亮
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentContext {

	@Resource
	private Map<Integer, PaymentStrategy> paymentStrategyMap;

	public String createOrder(List<String> myOrderIdList,
							  Integer platform,
							  String currencyCode,
							  String baseUrl) {

		// 获取支付平台
		PaymentStrategy paymentStrategy = this.paymentStrategyMap.get(platform);
		// 创建订单
		return paymentStrategy.createOrder(myOrderIdList,
				platform,
				currencyCode,
				baseUrl);

	}

	public void capture(Integer platform, String platformOrderId) {

		// 获取支付平台
		PaymentStrategy paymentStrategy = this.paymentStrategyMap.get(platform);
		paymentStrategy.capture(platformOrderId);

	}

	public void refund(Integer platform,
					   String captureId,
					   String currencyCode,
					   String amount) {

		// 获取支付平台
		PaymentStrategy paymentStrategy = this.paymentStrategyMap.get(platform);
		paymentStrategy.refund(captureId, currencyCode, amount);

	}

}