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
 * 流水明细表
 *
 * @author 姚壬亮
 **/
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("Detail")
public class DetailEntity extends BaseEntity {

	@ApiModelProperty(value = "流水单表主键")
	@TableField
	Long streamId;

	@ApiModelProperty(value = "流水明细编号")
	@TableField
	String sn;

	@ApiModelProperty(value = "本平台的订单Id")
	@TableField
	Long myOrderId;

	@ApiModelProperty(value = "第三方支付平台的捕获付款Id")
	@TableField
	String captureId;

	@ApiModelProperty(value = "币种")
	@TableField
	String currencyCode;

	@ApiModelProperty(value = "支付金额")
	@TableField
	BigDecimal amount;

}
