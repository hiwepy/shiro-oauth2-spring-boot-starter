/*
 * 版权所有.(c)2008-2018. 极蚁网络工作室 (http://jeebiz.net).
 */
package org.apache.shiro.spring.boot;


import com.github.scribejava.apis.WeiXinApi20;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;

public class WeiXinExample {

	public static void main(String[] args) {

		final String apiKey = "x";
		final String apiSecret = "x ";
		final OAuth20Service service = new ServiceBuilder(null)
				.apiKey(apiKey)
				.apiSecret(apiSecret)
				.callback("http://www.yichisancun.com/qqlogin.htm")
				.defaultScope("get_user_info,list_album,upload_pic,do_like")
				.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:34.0) Gecko/20100101 Firefox/34.0")
				.build(WeiXinApi20.instance());
		System.out.println(service.getAuthorizationUrl());
	}
	
}
