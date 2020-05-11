package com.free.yrl.demospringbootpayment.platform.paypal.config;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * PayPal相关配置
 *
 * @author 姚壬亮
 **/
@Configuration
public class PaypalConfig {

	@Value("${spring.profiles.active}")
	private String active;

	@Value("${paypal.client-id}")
	private String clientId;

	@Value("${paypal.secret}")
	private String secret;

	/*创建沙盒环境*/
	@Bean
	public PayPalEnvironment payPalEnvironment() {

		if ("pro".equals(active)) {
			return new PayPalEnvironment.Live(clientId, secret);
		}
		return new PayPalEnvironment.Sandbox(clientId, secret);

	}

	/*为环境创建客户端*/
	@Bean
	public PayPalHttpClient payPalHttpClient() {

		return new PayPalHttpClient(payPalEnvironment());

	}

}