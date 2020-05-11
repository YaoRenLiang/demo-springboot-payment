package com.free.yrl.demospringbootpayment.base.i18n;

import com.google.common.collect.Maps;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import static org.springframework.util.ObjectUtils.isEmpty;

/**
 * 状态码国际化
 *
 * @author 姚壬亮
 **/
@PropertySource(value = {"classpath:i18n/ResponseMessage*.properties"})
public class Resources {

	private static final Map<String, ResourceBundle> MESSAGES = Maps.newHashMap();

	/**
	 * 获取国际化信息
	 */
	public static String getMessage(String key, Object... params) {
		/*获取语言，这个语言是从header中的Accept-Language中获取的，
		会根据Accept-Language的值生成符合规则的locale，如zh、pt、en等*/
		Locale locale = LocaleContextHolder.getLocale();
		ResourceBundle message = MESSAGES.get(locale.getLanguage());
		if (message == null) {
			synchronized (MESSAGES) {
				// 在这里读取配置信息
				message = MESSAGES.get(locale.getLanguage());
				if (message == null) {
					message = ResourceBundle.getBundle("i18n/ResponseMessage", locale);
					MESSAGES.put(locale.getLanguage(), message);
				}
			}
		}
		// 此处获取并返回message
		if (isEmpty(params)) {
			return String.format(message.getString(key), params);
		}
		return message.getString(key);
	}

	/**
	 * 清除国际化信息
	 */
	public static void flushMessage() {
		MESSAGES.clear();
	}

}