package com.free.yrl.demospringbootpayment.platform.paypal.util;

import com.free.yrl.demospringbootpayment.platform.paypal.condition.ItemCondition;
import com.free.yrl.demospringbootpayment.platform.paypal.condition.PayPalCondition;
import com.free.yrl.demospringbootpayment.platform.paypal.condition.PayPalDetailedCondition;
import com.google.common.collect.Lists;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.http.exceptions.HttpException;
import com.paypal.orders.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * PayPal订单工具类
 *
 * @author 姚壬亮
 **/
@Slf4j
@Component
public class PayPalOrderUtils {

	@Resource
	private PayPalHttpClient payPalHttpClient;

	private PayPalCondition payPalCondition = null;

	private PayPalDetailedCondition payPalDetailedCondition = null;

	/**
	 * 创建PayPal订单
	 * 推荐支付步骤：
	 * 1.先创建本平台订单并生成订单号
	 * 2.订单创建成功之后，创建PayPal订单，并把本平台订单号追加到cancelUrl、returnUrl之后，
	 * 例子：www.yrl.com/success/myOrderIdxxxxxx
	 * 3.拿到PayPal订单号更新到本平台订单中，并把授权支付链接发送给前端
	 * 4.付款成功后，跳转到本平台并且链接后缀携带本平台订单号，通过本平台订单号拿到PayPal订单号进行扣款操作
	 * 如果用户取消付款，则直接更新订单状态。一定要记得通过PayPal订单号做幂等性。
	 *
	 * @param payPalCondition PayPal入参
	 * @return 订单信息
	 */
	public Order createOrder(PayPalCondition payPalCondition) {

		this.payPalCondition = payPalCondition;
		Order order = null;
		OrderRequest orderRequest = getOrderRequest();
		OrdersCreateRequest request = new OrdersCreateRequest()
				.requestBody(orderRequest);
		try {
			// 与客户机一起调用API并获取对调用的响应
			HttpResponse<Order> response = payPalHttpClient.execute(request);
			// 获取结果
			order = response.result();
		} catch (IOException ioe) {
			if (ioe instanceof HttpException) {
				// 服务器端出错
				HttpException he = (HttpException) ioe;
				log.error(he.getMessage());
				he.headers().forEach(x -> log.error(x + " :" + he.headers().header(x)));
			} else {
				// 客户端出错
				ioe.printStackTrace();
				log.error(ioe.getMessage());
			}
		}
		return order;

	}

	/**
	 * 捕获扣款
	 *
	 * @param payPalOrderId 订单创建成功之后，PayPal生成的订单Id
	 * @return 退款信息
	 */
	public Order capture(String payPalOrderId) {

		Order order = null;
		// 创建一个捕获订单请求
		OrdersCaptureRequest request = new OrdersCaptureRequest(payPalOrderId);
		try {
			HttpResponse<Order> response = payPalHttpClient.execute(request);
			order = response.result();
			// 记录PayPal captureId，退款需要用到
			List<PurchaseUnit> purchaseUnitList = order.purchaseUnits();
			for (PurchaseUnit purchaseUnit : purchaseUnitList) {
				log.info("myOrderId{}对应的captureId{}", purchaseUnit.referenceId(), purchaseUnit.payments().captures().get(0).id());
			}
			order.purchaseUnits().get(0).payments().captures().get(0).links()
					.forEach(link -> System.out.println(link.rel() + " => " + link.method() + ":" + link.href()));
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
		return order;

	}

	/**
	 * 获取订单请求详细信息
	 *
	 * @return 订单请求详细信息
	 */
	private OrderRequest getOrderRequest() {

		// 这里创建对/v2/checkout/orders的POST请求
		OrderRequest orderRequest = new OrderRequest();
		// 自定义付款人体验
		orderRequest.applicationContext(getApplicationContext());
		// 在订单创建后立即获取付款或授权付款的意图
		orderRequest.checkoutPaymentIntent(payPalCondition.getPaymentIntent());
		orderRequest.payer(getPayer());
		// 一组购买信息
		List<PurchaseUnitRequest> purchaseUnits = Lists.newArrayList();
		List<PayPalDetailedCondition> payPalDetailedConditionList = payPalCondition.getPayPalDetailedConditionList();
		for (PayPalDetailedCondition payPalDetailedCondition : payPalDetailedConditionList) {
			purchaseUnits.add(getPurchaseUnitRequest(payPalDetailedCondition));
		}
		orderRequest.purchaseUnits(purchaseUnits);
		return orderRequest;

	}

	/**
	 * 获取自定义付款人体验
	 *
	 * @return 自定义付款人体验
	 */
	private ApplicationContext getApplicationContext() {

		return new ApplicationContext()
				.cancelUrl(payPalCondition.getCancelUrl())
				.returnUrl(payPalCondition.getReturnUrl())
				.landingPage(payPalCondition.getLandingPage())
				.paymentMethod(getPaymentMethod())
				.shippingPreference(payPalCondition.getShippingPreference())
				.userAction(payPalCondition.getUserAction());

	}

	private Payer getPayer() {

		return new Payer().email(payPalCondition.getEmail());

	}

	/**
	 * 获取客户和商家的付款首选项。
	 *
	 * @return 客户和商家的付款首选项
	 */
	private PaymentMethod getPaymentMethod() {

		return new PaymentMethod()
				.payeePreferred(payPalCondition.getPayeePreferred());

	}

	/**
	 * 采购单元请求。包括付款合同所需的信息。
	 *
	 * @param payPalDetailedCondition PayPal购买详情入参
	 * @return 采购信息
	 */
	private PurchaseUnitRequest getPurchaseUnitRequest(PayPalDetailedCondition payPalDetailedCondition) {

		this.payPalDetailedCondition = payPalDetailedCondition;
		AmountWithBreakdown amountWithBreakdown = new AmountWithBreakdown()
				.amountBreakdown(getAmountBreakdown())
				.currencyCode(payPalDetailedCondition.getCurrencyCode())
				.value(getTotalAmount());
		List<Item> items = getItems();
		return new PurchaseUnitRequest()
				.amountWithBreakdown(amountWithBreakdown)
				/*API调用者提供的外部ID。用于协调客户交易与PayPal交易，
				出现在交易和结算报告中，但对付款人不可见*/
				.customId(payPalDetailedCondition.getMyOrderId())
				.description(payPalDetailedCondition.getDescription())
				.invoiceId(payPalDetailedCondition.getInvoiceId())
				.items(items)
				// API调用方为购买单元提供了外部ID
				.referenceId(payPalDetailedCondition.getMyOrderId());

	}

	/**
	 * 金额的明细。明细表提供了项目总金额、总税额、运输、处理、保险和折扣（如果有）等详细信息。
	 *
	 * @return 金额的明细
	 */
	private AmountBreakdown getAmountBreakdown() {

		AmountBreakdown amountBreakdown = new AmountBreakdown();
		String currencyCode = payPalDetailedCondition.getCurrencyCode();
		// 在给定的购买单位内所有商品的折扣
		amountBreakdown.discount(getMoney(currencyCode, payPalDetailedCondition.getDiscount()));
		// 一个采购单元内所有项目的手续费
		amountBreakdown.handling(getMoney(currencyCode, payPalDetailedCondition.getHandling()));
		// 购买单位内所有物品的保险费
		amountBreakdown.insurance(getMoney(currencyCode, payPalDetailedCondition.getInsurance()));
		// 给定采购单位内所有项目的运费
		amountBreakdown.shipping(getMoney(currencyCode, payPalDetailedCondition.getShipping()));
		// 给定范围内所有项目的运输折扣
		amountBreakdown.shippingDiscount(getMoney(currencyCode, payPalDetailedCondition.getShippingDiscount()));
		/*所有项目的小计。如果请求包含 purchase_units[].items[].unit_amount ，则为必需。
		必须等于所有项的（items[].unit_amount * items[].quantity）之和。*/
		amountBreakdown.itemTotal(getMoney(currencyCode, payPalDetailedCondition.getItemTotal()));
		/*所有项目的总税额。如果请求包含，则为必需purchase_units.items.tax。
		必须等于(items[].tax * items[].quantity)所有项目的总和。*/
		amountBreakdown.taxTotal(getMoney(currencyCode, payPalDetailedCondition.getTaxTotal()));
		return amountBreakdown;

	}

	private Money getMoney(String currencyCode, String money) {

		return new Money()
				.currencyCode(currencyCode)
				.value(money);

	}

	/**
	 * 客户从商家购买的一系列商品规格
	 *
	 * @return 一组规格
	 */
	private List<Item> getItems() {

		List<Item> items = Lists.newArrayList();
		List<ItemCondition> itemConditionList = payPalDetailedCondition.getItemConditionList();
		for (ItemCondition itemCondition : itemConditionList) {
			String quantity = itemCondition.getQuantity();
			if (Strings.isBlank(quantity) || !(Integer.parseInt(quantity) > 0)) {
				log.error("购买数量必须大于0");
				return Lists.newArrayList();
			}
			items.add(new Item()
					.description(itemCondition.getDescription())
					.name(itemCondition.getName())
					.quantity(itemCondition.getQuantity())
					.sku(itemCondition.getSku())
					.tax(getMoney(payPalDetailedCondition.getCurrencyCode(), itemCondition.getTax()))
					.unitAmount(getMoney(payPalDetailedCondition.getCurrencyCode(), itemCondition.getUnitAmount())));
		}
		return items;

	}

	/**
	 * 订单总金额，所有项目的小计。如果请求包含AmountBreakdown，
	 * “项目合计”加上“税款合计”加上“运输”加上“处理”加上“保险”减去“运输”折扣的总和
	 *
	 * @return 订单总金额
	 */
	private String getTotalAmount() {

		BigDecimal itemTotal = new BigDecimal(payPalDetailedCondition.getItemTotal());
		BigDecimal handling = new BigDecimal(payPalDetailedCondition.getHandling());
		BigDecimal insurance = new BigDecimal(payPalDetailedCondition.getInsurance());
		BigDecimal shipping = new BigDecimal(payPalDetailedCondition.getShipping());
		BigDecimal taxTotal = new BigDecimal(payPalDetailedCondition.getTaxTotal());
		BigDecimal discount = new BigDecimal(payPalDetailedCondition.getDiscount());
		BigDecimal shippingDiscount = new BigDecimal(payPalDetailedCondition.getShippingDiscount());
		BigDecimal totalAmount = itemTotal.add(handling).add(insurance).add(shipping).add(taxTotal).subtract(discount).subtract(shippingDiscount);
		int i = totalAmount.compareTo(BigDecimal.ZERO);
		if (i < 0) {
			// num小于0
			return "0";
		} else {
			// num大于等于0
			return totalAmount.toString();
		}

	}

}