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
public class StreamBillEntity extends BaseEntity {

	@ApiModelProperty(value = "商户订单流水号")
	String sn;

	@ApiModelProperty(value = "订单流水单表主键")
	Long streamId;

	@ApiModelProperty(value = "本平台的订单Id")
	Long myOrderId;

	@ApiModelProperty(value = "第三方支付平台的捕获付款Id")
	String captureId;

	@ApiModelProperty(value = "币种")
	String currency;

	@ApiModelProperty(value = "支付金额")
	BigDecimal amount;

}
