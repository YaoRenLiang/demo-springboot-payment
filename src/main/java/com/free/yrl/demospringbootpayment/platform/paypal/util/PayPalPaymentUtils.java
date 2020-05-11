package com.free.yrl.demospringbootpayment.platform.paypal.util;

import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.http.exceptions.HttpException;
import com.paypal.payments.CapturesRefundRequest;
import com.paypal.payments.Money;
import com.paypal.payments.Refund;
import com.paypal.payments.RefundRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * PayPal工具类
 *
 * @author 姚壬亮
 **/
@Slf4j
@Component
public class PayPalPaymentUtils {

	@Resource
	private PayPalHttpClient payPalHttpClient;

	/**
	 * 退款
	 *
	 * @param captureId    创建订单成功之后的captureId
	 * @param currencyCode 退款的币种，详情参考枚举 PayPalCurrencyConstants
	 * @param money        退款的金额
	 * @return 退款信息
	 */
	public Refund refund(String captureId, String currencyCode, String money) {
		Refund refund = null;
		try {
			RefundRequest refundRequest = new RefundRequest();
			if (Strings.isNotBlank(currencyCode) && Strings.isNotBlank(money)) {
				// 退款金额
				refundRequest.amount(getMoney(currencyCode, money));
			}
			CapturesRefundRequest capturesRefundRequest = new CapturesRefundRequest(captureId)
					.requestBody(refundRequest);
			// 与客户机一起调用API并获取对调用的响应
			HttpResponse<Refund> response = payPalHttpClient.execute(capturesRefundRequest);
			refund = response.result();
			log.info("退款信息: " + refund);
			return refund;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			if (ioe instanceof HttpException) {
				// 服务器端出错
				HttpException he = (HttpException) ioe;
				System.out.println(he.getMessage());
			} else {
				// 客户端出错
				log.error(ioe.getMessage());
			}
		}
		return refund;
	}

	private Money getMoney(String currencyCode, String money) {

		return new Money()
				.currencyCode(currencyCode)
				.value(money);

	}

}