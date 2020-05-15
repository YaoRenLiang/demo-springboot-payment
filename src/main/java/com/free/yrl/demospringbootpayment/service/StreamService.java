package com.free.yrl.demospringbootpayment.service;

import com.free.yrl.demospringbootpayment.base.ResponseMessage;
import com.free.yrl.demospringbootpayment.entity.StreamEntity;
import com.paypal.orders.Order;
import com.paypal.payments.Refund;

import java.util.List;

/**
 * @author 姚壬亮
 **/
public interface StreamService {

	Integer insert(StreamEntity streamEntity);

	Integer updateById(StreamEntity streamEntity);

	/**
	 * 关闭流水单
	 *
	 * @param id 流水单表主键
	 * @return 状态码
	 */
	Integer close(Long id);

	ResponseMessage<StreamEntity> selectById(Long id);

}
