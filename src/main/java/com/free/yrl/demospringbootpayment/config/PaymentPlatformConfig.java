package com.free.yrl.demospringbootpayment.config;

import com.free.yrl.demospringbootpayment.constant.PaymentPlatformConstants;
import com.free.yrl.demospringbootpayment.platform.paypal.PayPalStrategy;
import com.free.yrl.demospringbootpayment.strategy.PaymentStrategy;
import com.google.common.collect.Maps;
import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * 支付平台配置
 *
 * @author 姚壬亮
 **/
@Configuration
public class PaymentPlatformConfig {

	/**
	 * 支付平台注入
	 *
	 * @return map
	 */
	@Bean
	public Map<Integer, PaymentStrategy> paymentPlatformMap() {

		Map<Integer, PaymentStrategy> map = Maps.newHashMap();
		map.put(PaymentPlatformConstants.PAYPAL.getKey(), new PayPalStrategy());
		map.put(PaymentPlatformConstants.ALIPAY.getKey(), new PayPalStrategy());
		return map;

	}

}
