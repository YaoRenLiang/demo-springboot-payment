package com.free.yrl.demospringbootpayment.strategy;

import com.free.yrl.demospringbootpayment.base.SnowFlake;
import com.free.yrl.demospringbootpayment.constant.ResponseMessageConstants;
import com.free.yrl.demospringbootpayment.entity.StreamEntity;
import com.free.yrl.demospringbootpayment.service.StreamService;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * 支付抽象
 *
 * @author 姚壬亮
 */
@Slf4j
public abstract class PaymentStrategy {

	@Resource
	private SnowFlake snowFlake;

	@Resource
	private StreamService streamServiceImpl;

	/**
	 * 获取授权支付信息
	 *
	 * @param myOrderIdList 一组本平台的orderId
	 * @param platform      支付平台，详情参考枚举 PlatformConstants
	 * @param currencyCode  币种，详情参考枚举 PayPalCurrencyConstants
	 * @param baseUrl       访问本平台url前缀，例如www.yrl.com
	 * @return 授权支付链接
	 */
	public abstract String createOrder(List<String> myOrderIdList,
									   Integer platform,
									   String currencyCode,
									   String baseUrl);

	/**
	 * 捕获订单
	 *
	 * @param platformOrderId 支付平台的订单主键
	 */
	public abstract void capture(String platformOrderId);

	/**
	 * 退款
	 *
	 * @param captureId    创建订单成功之后的captureId
	 * @param currencyCode 退款的币种，详情参考枚举 PayPalCurrencyConstants
	 * @param amount       退款的金额
	 */
	public abstract void refund(String captureId,
								String currencyCode,
								String amount);

	public Long insertStream(Integer platform,
							 String currencyCode) {

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
			id = null;
		}
		return id;

	}

	public Integer updateStreamById(Long id,
									String platformOrderId) {

		log.info("Platform Order ID{} ", platformOrderId);
		// 记录付款相关信息，创建订单流水
		return streamServiceImpl.updateById(StreamEntity.builder()
				.id(id)
				.platformOrderId(platformOrderId)
				.build());

	}

} 