package com.free.yrl.demospringbootpayment.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Url工具类
 *
 * @author 姚壬亮
 **/
public class UrlUtils {

	/**
	 * 获取请求的基础路径
	 *
	 * @param request 请求
	 * @return 基础路径
	 */
	public static String getBaseUrl(HttpServletRequest request) {
		// 返回用于发出此请求的方案的名称，例如：http，https，ftp
		String scheme = request.getScheme();
		// 返回向其发送请求的服务器的主机名，例如：127.0.0.1，localhost，baidu.com
		String serverName = request.getServerName();
		// 返回向其发送请求的服务器的端口，例如：9001，8080，9000
		int serverPort = request.getServerPort();
		String contextPath = request.getContextPath();
		StringBuilder url = new StringBuilder();
		url.append(scheme).append("://").append(serverName);
		if ((serverPort != 80) && (serverPort != 443)) {
			url.append(":").append(serverPort);
		}
		url.append(contextPath);
		if (url.toString().endsWith("/")) {
			url.append("/");
		}
		return url.toString();
	}

}