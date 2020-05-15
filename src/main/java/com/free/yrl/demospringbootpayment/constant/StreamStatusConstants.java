package com.free.yrl.demospringbootpayment.constant;

import lombok.Getter;

/**
 * 流水单表状态
 *
 * @author 姚壬亮
 **/
@Getter
public enum StreamStatusConstants {

	noOk(1, "未完成"),
	doing(2, "处理中"),
	ok(3, "已完成"),
	cancel(4, "已取消");

	StreamStatusConstants(Integer key, String value) {
		this.key = key;
		this.value = value;
	}

	private Integer key;
	private String value;

}