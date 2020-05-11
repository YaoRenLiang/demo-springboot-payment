package com.free.yrl.demospringbootpayment.base;

import com.free.yrl.demospringbootpayment.base.i18n.Resources;
import com.free.yrl.demospringbootpayment.constant.ResponseMessageConstants;
import com.google.common.collect.Maps;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import static org.springframework.util.ObjectUtils.isEmpty;

@Getter
@ApiModel(value = "response", description = "响应结果")
public class ResponseMessage<T> implements Serializable {

	@ApiModelProperty(value = "状态码", required = true)
	private Integer code;

	@ApiModelProperty("调用结果消息")
	private String message;

	@ApiModelProperty(value = "响应数据")
	private T data;

	private ResponseMessage(Integer code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
		if (isEmpty(code)) {
			this.code = ResponseMessageConstants.SERVICEEXCEPTION.getKey();
		}
		if (isEmpty(message)) {
			this.message = ResponseMessageConstants.SERVICEEXCEPTION.getValue();
		}
	}

	/*返回错误*/
	public static <T> ResponseMessage<T> error(ResponseMessageConstants constants) {
		Integer key = constants.getKey();
		return new ResponseMessage(key, msg(key), null);
	}

	/*返回错误*/
	public static <T> ResponseMessage<T> error(Integer code) {
		return new ResponseMessage(code, msg(code), null);
	}

	/*返回错误*/
	public static <T> ResponseMessage<T> error(Integer code, String message) {
		return new ResponseMessage(code, msg(code, message), null);
	}

	/*返回成功*/
	public static <T> ResponseMessage<T> success() {
		Integer key = ResponseMessageConstants.SUCCESSFULOPERATION.getKey();
		return new ResponseMessage(key, msg(key), null);
	}

	/*返回成功*/
	public static <T> ResponseMessage<T> success(Object data) {
		Integer key = ResponseMessageConstants.SUCCESSFULOPERATION.getKey();
		if (data instanceof String || data instanceof Integer || data instanceof Date) {
			Map map = Maps.newHashMap();
			map.put("result", data);
			return new ResponseMessage(key, msg(key), map);
		}
		if (isEmpty(data)) {
			data = null;
		}
		return new ResponseMessage(key, msg(key), data);
	}

	private static String msg(Integer key, Object... params) {
		return Resources.getMessage("CODE_" + key, params);
	}

}