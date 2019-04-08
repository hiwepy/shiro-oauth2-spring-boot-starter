/*
 * 版权所有.(c)2008-2018. 极蚁网络工作室 (http://jeebiz.net).
 */
package org.apache.shiro.spring.boot;

import com.github.scribejava.apis.QQApi20;
import com.github.scribejava.apis.SinaWeiboApi20;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;

public class QQExample {

	public static void main(String[] args) {

		final String apiKey = "101303927";
		final String apiSecret = "0c3ac6430d6e2f60dfb637101252417e ";
		final OAuth20Service service = new ServiceBuilder(null)
				.apiKey(apiKey)
				.apiSecret(apiSecret)
				.callback("http://www.yichisancun.com/qqlogin.htm")
				.defaultScope("get_user_info,list_album,upload_pic,do_like")
				.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:34.0) Gecko/20100101 Firefox/34.0")
				.build(QQApi20.instance());

		System.out.println(service.getAuthorizationUrl());
	}
}
