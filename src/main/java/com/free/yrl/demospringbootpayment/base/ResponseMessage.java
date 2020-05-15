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

/**
 * 返回结构体
 *
 * @author 姚壬亮
 **/
@Getter
@ApiModel(value = "response", description = "响应结果")
public class ResponseMessage<T> implements Serializable {

	@ApiModelProperty(value = "状态码", required = true)
	private Integer code;

	@ApiModelProperty("调用结果消息")
	private String message;

	@ApiModelProperty(value = "响应数据")
	private final T data;

	private ResponseMessage(Integer code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
		if (isEmpty(code)) {
			this.code = ResponseMessageConstants.SERVICE_EXCEPTION.getKey();
		}
		if (isEmpty(message)) {
			this.message = ResponseMessageConstants.SERVICE_EXCEPTION.getValue();
		}
	}

	/**
	 * 控制层返回处理
	 *
	 * @param resultCode 结果状态码
	 * @return 状态码
	 */
	public static <T> ResponseMessage<T> controllerReturn(Integer resultCode) {

		if (!resultCode.equals(ResponseMessageConstants.SUCCESS_OPERATION.getKey())) {
			return error(resultCode);
		}
		return success();

	}

	/**
	 * 服务层返回处理
	 *
	 * @param successRow 成功的行数（c u d操作）
	 * @return 状态码
	 */
	public static Integer cudReturn(int successRow) {

		if (successRow <= 0) {
			return ResponseMessageConstants.SERVICE_EXCEPTION.getKey();
		}
		return ResponseMessageConstants.SUCCESS_OPERATION.getKey();

	}

	/**
	 * 返回错误
	 *
	 * @param constants 枚举
	 * @param <T>       范性
	 * @return 返回体
	 */
	public static <T> ResponseMessage<T> error(ResponseMessageConstants constants) {

		Integer key = constants.getKey();
		return error(key);

	}

	/**
	 * 返回错误
	 *
	 * @param code 状态码
	 * @param <T>  范性
	 * @return 返回体
	 */
	public static <T> ResponseMessage<T> error(Integer code) {

		return new ResponseMessage(code, msg(code), null);

	}

	/**
	 * 返回成功
	 *
	 * @param <T> 范性
	 * @return 返回体
	 */
	public static <T> ResponseMessage<T> success() {

		Integer key = ResponseMessageConstants.SUCCESS_OPERATION.getKey();
		return new ResponseMessage(key, msg(key), null);

	}

	/**
	 * 返回成功
	 *
	 * @param data 需要返回的数据
	 * @param <T>  范性
	 * @return 返回体
	 */
	public static <T> ResponseMessage<T> success(Object data) {

		Integer key = ResponseMessageConstants.SUCCESS_OPERATION.getKey();
		if (data instanceof String || data instanceof Integer || data instanceof Date) {
			Map<String, Object> map = Maps.newHashMap();
			map.put("data", data);
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