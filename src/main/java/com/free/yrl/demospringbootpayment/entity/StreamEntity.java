package com.free.yrl.demospringbootpayment.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * 流水单表
 *
 * @author 姚壬亮
 **/
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("Stream")
public class StreamEntity extends BaseEntity {

	@ApiModelProperty(value = "流水单编号")
	@TableField
	String sn;

	@ApiModelProperty(value = "类型：1.收入、2.支出")
	@TableField
	Integer type;

	@ApiModelProperty(value = "第三方支付平台：1.PayPal、2.Alipay、3.WeChat。" +
			"支付平台，详情参考枚举 PlatformConstants")
	@TableField
	Integer platform;

	@ApiModelProperty(value = "第三方支付平台的订单Id")
	@TableField
	String platformOrderId;

	@ApiModelProperty(value = "交易完成时间")
	@TableField
	Date finishTime;

	@ApiModelProperty(value = "币种")
	@TableField
	String currencyCode;

	@ApiModelProperty(value = "流水总金额")
	@TableField
	BigDecimal amount;

	@ApiModelProperty(value = "状态：1.未完成、2.操作中、3.已完成、4.已关闭")
	@TableField
	Integer status;

}
