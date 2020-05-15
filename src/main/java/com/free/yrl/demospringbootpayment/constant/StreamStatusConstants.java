package com.free.yrl.demospringbootpayment.constant;

import lombok.Getter;

/**
 * 流水单表状态
 *
 * @author 姚壬亮
 **/
@Getter
public enum StreamStatusConstants {

	NO_OK(1, "未完成"),
	DOING(2, "处理中"),
	OK(3, "已完成"),
	CANCEL(4, "已取消");

	StreamStatusConstants(Integer key, String value) {
		this.key = key;
		this.value = value;
	}

	private Integer key;
	private String value;

}