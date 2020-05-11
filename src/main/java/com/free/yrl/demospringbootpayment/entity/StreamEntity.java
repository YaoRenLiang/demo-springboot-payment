package com.free.yrl.demospringbootpayment.entity;

import com.free.yrl.demospringbootpayment.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 姚壬亮
 **/
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StreamEntity extends BaseEntity {

	@ApiModelProperty(value = "商户订单流水号")
	String sn;

	@ApiModelProperty(value = "类型：1.收入、2.支出")
	Integer type;

	@ApiModelProperty(value = "第三方支付平台：1.PayPal、2.Alipay、3.WeChat。" +
			"付款类型，详情参考枚举 PaymentTypeConstants")
	Integer platform;

	@ApiModelProperty(value = "第三方支付平台的订单Id")
	String platformOrderId;

	@ApiModelProperty(value = "交易完成时间")
	Date finishTime;

	@ApiModelProperty(value = "币种")
	String currency;

	@ApiModelProperty(value = "流水总金额")
	BigDecimal amount;

	@ApiModelProperty(value = "状态：1.未完成、2.操作中、3.已完成、4.已关闭")
	Integer status;

}
