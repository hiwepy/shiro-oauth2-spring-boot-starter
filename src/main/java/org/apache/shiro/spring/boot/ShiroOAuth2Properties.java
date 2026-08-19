/*
 * Copyright (c) 2018, hiwepy (https://github.com/easy-4-java).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.apache.shiro.spring.boot;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>Configuration properties.</p>
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
@ConfigurationProperties(ShiroOAuth2Properties.PREFIX)
public class ShiroOAuth2Properties {

	public static final String PREFIX = "shiro.oauth2";
	
	/**
	 * Enable Shiro.
	 */
	private boolean enabled = false;
	
	private boolean cachingEnabled;

	/**
	 * The cache used by this realm to store AuthorizationInfo instances associated
	 * with individual Subject principals.
	 */
	private boolean authorizationCachingEnabled;
	private String authorizationCacheName;

	private boolean authenticationCachingEnabled;
	private String authenticationCacheName;

	/** 登录地址：会话不存在时访问的地址 */
	private String loginUrl;
	/** 重定向地址：会话注销后的重定向地址 */
    private String redirectUrl;
	/** 系统主页：登录成功后跳转路径 */
    private String successUrl;
    /** 异常页面：无权限时的跳转路径 */
    private String unauthorizedUrl;
	
	private Map<String /* pattert */, String /* Chain names */> filterChainDefinitionMap = new LinkedHashMap<String, String>();
	
	
    private String baseUrl;
    
   // private String providerTypeParameter = ProvidersDefinition.DEFAULT_PROVIDER_TYPE_PARAMETER;
    // default roles applied to authenticated user
    private String defaultRoles;
    
    // default permissions applied to authenticated user
    private String defaultPermissions;
	
	/**
	 * Returns the enabled.
	 *
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * Sets the enabled.
	 *
	 * @param enabled the enabled
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * Returns the authorization cache name.
	 *
	 * @return the authorization cache name
	 */
	public String getAuthorizationCacheName() {
        return authorizationCacheName;
    }

    /**
     * Sets the authorization cache name.
     *
     * @param authorizationCacheName the authorization cache name
     */
    public void setAuthorizationCacheName(String authorizationCacheName) {
        this.authorizationCacheName = authorizationCacheName;
    }

    /**
     * Returns {@code true} if authorization caching should be utilized if a {@link CacheManager} has been
     * {@link #setCacheManager(org.apache.shiro.cache.CacheManager) configured}, {@code false} otherwise.
     * <p/>
     * The default value is {@code true}.
     *
     * @return {@code true} if authorization caching should be utilized, {@code false} otherwise.
     */
    public boolean isAuthorizationCachingEnabled() {
        return isCachingEnabled() && authorizationCachingEnabled;
    }

    /**
     * Sets whether or not authorization caching should be utilized if a {@link CacheManager} has been
     * {@link #setCacheManager(org.apache.shiro.cache.CacheManager) configured}, {@code false} otherwise.
     * <p/>
     * The default value is {@code true}.
     *
     * @param authenticationCachingEnabled the value to set
     */
    public void setAuthorizationCachingEnabled(boolean authenticationCachingEnabled) {
        this.authorizationCachingEnabled = authenticationCachingEnabled;
        if (authenticationCachingEnabled) {
            setCachingEnabled(true);
        }
    }
	    
	/**
     * Returns the name of a {@link Cache} to lookup from any available {@link #getCacheManager() cacheManager} if
     * a cache is not explicitly configured via {@link #setAuthenticationCache(org.apache.shiro.cache.Cache)}.
     * <p/>
     * This name will only be used to look up a cache if authentication caching is
     * {@link #isAuthenticationCachingEnabled() enabled}.
     * <p/>
     * <b>WARNING:</b> Only set this property if safe caching conditions apply, as documented at the top
     * of this page in the class-level JavaDoc.
     *
     * @return the name of a {@link Cache} to lookup from any available {@link #getCacheManager() cacheManager} if
     *         a cache is not explicitly configured via {@link #setAuthenticationCache(org.apache.shiro.cache.Cache)}.
     * @see #isAuthenticationCachingEnabled()
     * @since 1.2
     */
    public String getAuthenticationCacheName() {
        return this.authenticationCacheName;
    }

    /**
     * Sets the name of a {@link Cache} to lookup from any available {@link #getCacheManager() cacheManager} if
     * a cache is not explicitly configured via {@link #setAuthenticationCache(org.apache.shiro.cache.Cache)}.
     * <p/>
     * This name will only be used to look up a cache if authentication caching is
     * {@link #isAuthenticationCachingEnabled() enabled}.
     *
     * @param authenticationCacheName the name of a {@link Cache} to lookup from any available
     *                                {@link #getCacheManager() cacheManager} if a cache is not explicitly configured
     *                                via {@link #setAuthenticationCache(org.apache.shiro.cache.Cache)}.
     * @see #isAuthenticationCachingEnabled()
     * @since 1.2
     */
    public void setAuthenticationCacheName(String authenticationCacheName) {
        this.authenticationCacheName = authenticationCacheName;
    }

    /**
     * Returns {@code true} if authentication caching should be utilized if a {@link CacheManager} has been
     * {@link #setCacheManager(org.apache.shiro.cache.CacheManager) configured}, {@code false} otherwise.
     * <p/>
     * The default value is {@code true}.
     *
     * @return {@code true} if authentication caching should be utilized, {@code false} otherwise.
     */
    public boolean isAuthenticationCachingEnabled() {
        return this.authenticationCachingEnabled && isCachingEnabled();
    }

    /**
     * Sets whether or not authentication caching should be utilized if a {@link CacheManager} has been
     * {@link #setCacheManager(org.apache.shiro.cache.CacheManager) configured}, {@code false} otherwise.
     * <p/>
     * The default value is {@code false} to retain backwards compatibility with Shiro 1.1 and earlier.
     * <p/>
     * <b>WARNING:</b> Only set this property to {@code true} if safe caching conditions apply, as documented at the top
     * of this page in the class-level JavaDoc.
     *
     * @param authenticationCachingEnabled the value to set
     */
    public void setAuthenticationCachingEnabled(boolean authenticationCachingEnabled) {
        this.authenticationCachingEnabled = authenticationCachingEnabled;
        if (authenticationCachingEnabled) {
            setCachingEnabled(true);
        }
    }
    
    /**
     * Returns {@code true} if caching should be used if a {@link CacheManager} has been
     * {@link #setCacheManager(org.apache.shiro.cache.CacheManager) configured}, {@code false} otherwise.
     * <p/>
     * The default value is {@code true} since the large majority of Realms will benefit from caching if a CacheManager
     * has been configured.  However, memory-only realms should set this value to {@code false} since they would
     * manage account data in memory already lookups would already be as efficient as possible.
     *
     * @return {@code true} if caching will be globally enabled if a {@link CacheManager} has been
     *         configured, {@code false} otherwise
     */
    public boolean isCachingEnabled() {
        return cachingEnabled;
    }

    /**
     * Sets whether or not caching should be used if a {@link CacheManager} has been
     * {@link #setCacheManager(org.apache.shiro.cache.CacheManager) configured}.
     *
     * @param cachingEnabled whether or not to globally enable caching for this realm.
     */
    public void setCachingEnabled(boolean cachingEnabled) {
        this.cachingEnabled = cachingEnabled;
    }

	/**
	 * Returns the login url.
	 *
	 * @return the login url
	 */
	public String getLoginUrl() {
		return loginUrl;
	}

	/**
	 * Sets the login url.
	 *
	 * @param loginUrl the login url
	 */
	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	/**
	 * Returns the redirect url.
	 *
	 * @return the redirect url
	 */
	public String getRedirectUrl() {
		return redirectUrl;
	}

	/**
	 * Sets the redirect url.
	 *
	 * @param redirectUrl the redirect url
	 */
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	/**
	 * Returns the success url.
	 *
	 * @return the success url
	 */
	public String getSuccessUrl() {
		return successUrl;
	}

	/**
	 * Sets the success url.
	 *
	 * @param successUrl the success url
	 */
	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}

	/**
	 * Returns the unauthorized url.
	 *
	 * @return the unauthorized url
	 */
	public String getUnauthorizedUrl() {
		return unauthorizedUrl;
	}

	/**
	 * Sets the unauthorized url.
	 *
	 * @param unauthorizedUrl the unauthorized url
	 */
	public void setUnauthorizedUrl(String unauthorizedUrl) {
		this.unauthorizedUrl = unauthorizedUrl;
	}

	public Map<String, String> getFilterChainDefinitionMap() {
		return filterChainDefinitionMap;
	}

	/**
	 * Sets the filter chain definition map.
	 *
	 * @param filterChainDefinitionMap the filter chain definition map
	 */
	public void setFilterChainDefinitionMap(Map<String, String> filterChainDefinitionMap) {
		this.filterChainDefinitionMap = filterChainDefinitionMap;
	}

	/**
	 * Returns the base url.
	 *
	 * @return the base url
	 */
	public String getBaseUrl() {
		return baseUrl;
	}

	/**
	 * Sets the base url.
	 *
	 * @param baseUrl the base url
	 */
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
    
	/**
	 * Returns the default roles.
	 *
	 * @return the default roles
	 */
	public String getDefaultRoles() {
		return defaultRoles;
	}

	/**
	 * Sets the default roles.
	 *
	 * @param defaultRoles the default roles
	 */
	public void setDefaultRoles(String defaultRoles) {
		this.defaultRoles = defaultRoles;
	}

	/**
	 * Returns the default permissions.
	 *
	 * @return the default permissions
	 */
	public String getDefaultPermissions() {
		return defaultPermissions;
	}

	/**
	 * Sets the default permissions.
	 *
	 * @param defaultPermissions the default permissions
	 */
	public void setDefaultPermissions(String defaultPermissions) {
		this.defaultPermissions = defaultPermissions;
	}

}

