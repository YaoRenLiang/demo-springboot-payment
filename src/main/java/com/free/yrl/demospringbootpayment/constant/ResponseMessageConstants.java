package com.free.yrl.demospringbootpayment.constant;

import lombok.Getter;

import java.io.Serializable;

/**
 * 定义一些基础返回字段
 *
 * @author 姚壬亮
 **/
@Getter
public enum ResponseMessageConstants implements Serializable {

	SUCCESSFULOPERATION(200, "Successful operation", "操作成功"),
	SERVICEEXCEPTION(-100, "Network is not good, please retry.", "服务异常"),
	PARAMETERERROR(-200, "Parameter error", "参数错误"),
	PARAMETERCANNOTBEEMPTY(-201, "Parameter cannot be empty", "参数不能为空"),
	SHOP_ID_PARAMETERCANNOTBEEMPTY(-2011, "shopId cannot be empty", "shopId不能为空"),
	PARAMETERFORMATNOTCORRECT(-202, "Parameter format not correct", "参数格式不正确"),
	CATEGORYID_PARAMETERFORMATNOTCORRECT(-2021, "categoryId should be long", "categoryId 应该为整数"),
	SHOPID_PARAMETERFORMATNOTCORRECT(-2022, "shopId should be long", "shopId 应该为整数"),
	TIMEALREADYSETTLED(-2101, "The selected time has been settled", "所选时间已结算"),
	RECEIPTFEE(-2102, "receipt fee must gte shop fee", "小票确认费用不能小于医院录入费用"),
	PASSWORDERROR(-203, "Password error", "密码错误"),
	SEARCHPAGEOVERERROR(-204, "Page over limit", "分页参数超出限度"),
	CODEERROR(-205, "Code error", "校验码错误"),
	CODENOEXIST(-206, "Code not exists", "校验码不存在"),
	REPETITIVEOPERATION(-300, "Please do not repeat operation", "请勿重复操作"),
	DATAERROR(-400, "Data error", "数据错误"),
	DATAINVALID(-401, "Data logged off", "数据已注销"),
	DATAFREEZE(-402, "Data frozen", "数据已冻结"),
	EMAILFREEZE(-4021, "Email frozen", "邮箱已冻结"),
	PHONEFREEZE(-4022, "Phone frozen", "手机号已冻结"),
	BILLINGDATAREPEAT(-4023, "billing data repeat", "账单数据重复"),
	BILLING_NOT_EXIT(-4025, "billing not exit", "账单不存在"),
	BILLING_SETTLED(-4024, "billing have settled", "账单已经结算"),
	EXIST(-403, "Data already exists", "数据已存在"),
	EMAILEXIST(-4031, "Email already exists", "邮箱已存在"),
	PHONEEXIST(-4032, "Phone already exists", "手机号已存在"),
	ACCOUNTEXIST(-4033, "Account already exists", "账号已存在"),
	NOEXIST(-404, "Data does not exist", "数据不存在"),
	SHOPEXIST(-405, "Store already exists", "店铺已存在"),
	PASSWORDEXIST(-406, "The current account password already exists, please log in directly", "当前账户密码已存在，请直接登陆"),
	ACCOUNTNOEXIST(-407, "Account does not exist", "账户不存在"),
	BINDRELATIONEXIST(-408, "Phone Bind exist", "手机号绑定关系已经存在"),
	OPENIDBINDRELATIONEXIST(-409, "OpenId Bind exist", "openid绑定关系已经存在"),
	ACCOUNTEXISTSHOP(-410, "Existing account data", "帐号已存在店铺"),
	ACCOUNTNOTEXISTEMAIL(-4100, "Existing account data", "帐号不存在邮箱，需要进行绑定操作"),
	STATUSRESTRICTIONS(-411, "Status restrictions", "状态限制"),
	PRIVILEGENOEXIST(-500, "Insufficient privilege", "权限不够"),
	SESSIONEXPIRATION(-501, "Session expiration", "会话过期"),
	RESOURCESINSUFFICIENT(-502, "Resources insufficient", "资源不足");


	private Integer key;
	private String value;
	private String china;

	private ResponseMessageConstants(Integer key, String value) {
		this.key = key;
		this.value = value;
	}

	private ResponseMessageConstants(Integer key, String value, String china) {
		this.key = key;
		this.value = value;
		this.china = china;
	}

	public static String getValueByKey(Integer key) {
		for (ResponseMessageConstants gender : ResponseMessageConstants.values()) {
			if (gender.getKey().equals(key)) {
				return gender.getValue();
			}
		}
		return "";
	}

	public static Integer getKeyByValue(Integer value) {
		for (ResponseMessageConstants gender : ResponseMessageConstants.values()) {
			if (value.equals(gender.getValue())) {
				return gender.getKey();
			}
		}
		return null;
	}

}