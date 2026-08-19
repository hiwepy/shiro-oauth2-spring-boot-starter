package org.apache.shiro.spring.boot.oauth1;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.Filter;

import org.apache.shiro.biz.spring.ShiroFilterProxyFactoryBean;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.springframework.beans.BeansException;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.ObjectUtils;

/**
 * 
 * @className	： ShiroOAuth2FilterFactoryBean
 * @description	： TODO(描述这个类的作用)
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @date		： 2018年2月8日 上午9:10:54
 * @version 	V1.0
 * @since 1.0.0
 */
public class ShiroOAuth2FilterFactoryBean extends ShiroFilterProxyFactoryBean implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	/**
	 * Returns the application context.
	 *
	 * @return the application context
	 */
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public ShiroOAuth2FilterFactoryBean() {
	}
	
	protected boolean supports(Filter filter) {
		return filter instanceof AccessControlFilter ||  filter instanceof LogoutFilter;
	}
	
	// 过滤器链：实现对路径规则的拦截过滤
	@Override
	public Map<String, Filter> getFilters() {

		Map<String, Filter> filters = new LinkedHashMap<String, Filter>();

		// Get Shiro filter beans directly from application context
		Map<String, AccessControlFilter> accessFilters = getApplicationContext()
				.getBeansOfType(AccessControlFilter.class);
		if (!ObjectUtils.isEmpty(accessFilters)) {
			filters.putAll(accessFilters);
		}
		Map<String, LogoutFilter> logoutFilters = getApplicationContext()
				.getBeansOfType(LogoutFilter.class);
		if (!ObjectUtils.isEmpty(logoutFilters)) {
			filters.putAll(logoutFilters);
		}

		filters.putAll(super.getFilters());

		return filters;

	}

	@Override
	/**
	 * Sets the application context.
	 *
	 * @param applicationContext the application context
	 * @throws BeansException if an error occurs
	 */
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}
