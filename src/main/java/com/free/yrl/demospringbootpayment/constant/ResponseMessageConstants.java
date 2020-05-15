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

	SUCCESS_OPERATION(0, "操作成功"),
	SERVICE_EXCEPTION(-100, "服务异常");


	private Integer key;
	private String value;

	ResponseMessageConstants(Integer key, String value) {
		this.key = key;
		this.value = value;
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