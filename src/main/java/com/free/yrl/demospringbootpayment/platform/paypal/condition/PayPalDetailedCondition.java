package com.free.yrl.demospringbootpayment.platform.paypal.condition;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.logging.log4j.util.Strings;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * PayPal购买详情入参
 *
 * @author 姚壬亮
 **/
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "PayPal购买详情入参")
public class PayPalDetailedCondition implements Serializable {

	@ApiModelProperty(value = "本平台的订单主键，不可重复", required = true)
	private String myOrderId;

	@ApiModelProperty(value = "币种，详情参考枚举 PayPalCurrencyConstants", required = true)
	private String currencyCode;

	@ApiModelProperty(value = "项目规格列表", required = true)
	private List<ItemCondition> itemConditionList;

	@ApiModelProperty(value = "所有项目的总折扣", required = false)
	private String discount;

	@ApiModelProperty(value = "所有项目的总手续费", required = false)
	private String handling;

	@ApiModelProperty(value = "所有项目的总保险费", required = false)
	private String insurance;

	@ApiModelProperty(value = "所有项目的总运费", required = false)
	private String shipping;

	@ApiModelProperty(value = "所有项目的总运费折扣", required = false)
	private String shippingDiscount;

	@ApiModelProperty(value = "购买说明，最大长度：127", required = false)
	private String description;

	@ApiModelProperty(value = "本订单提供的外部发票号。出现在付款人的交易历史和付款人收到的电子邮件中", required = false)
	private String invoiceId;

	@ApiModelProperty(value = "所有项目的合计")
	public String getItemTotal() {
		BigDecimal itemTotal = new BigDecimal("0");
		for (ItemCondition itemCondition : itemConditionList) {
			String quantity = itemCondition.getQuantity();
			String unitAmount = itemCondition.getUnitAmount();
			// 商品单价不能为空
			if (Strings.isBlank(unitAmount)) {
				unitAmount = "0";
			}
			BigDecimal total = new BigDecimal(quantity).multiply(new BigDecimal(unitAmount));
			itemTotal = itemTotal.add(total);
		}
		return itemTotal.toString();
	}

	@ApiModelProperty(value = "所有项目的总税额")
	public String getTaxTotal() {
		BigDecimal taxTotal = new BigDecimal("0");
		for (ItemCondition itemCondition : itemConditionList) {
			String quantity = itemCondition.getQuantity();
			String tax = itemCondition.getTax();
			// 商品税额不能为空
			if (Strings.isBlank(tax)) {
				tax = "0";
			}
			BigDecimal total = new BigDecimal(quantity).multiply(new BigDecimal(tax));
			taxTotal = taxTotal.add(total);
		}
		return taxTotal.toString();
	}

}
