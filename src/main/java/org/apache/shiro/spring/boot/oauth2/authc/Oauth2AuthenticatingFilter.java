/*
 * Copyright (c) 2018, hiwepy (https://github.com/hiwepy).
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
package org.apache.shiro.spring.boot.oauth2.authc;


import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.biz.web.filter.authc.AbstractAuthenticatingFilter;
import org.apache.shiro.spring.boot.oauth2.exception.OAuth2AuthenticationException;
import org.apache.shiro.spring.boot.oauth2.token.OAuth2Token;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.oauth.OAuth20Service;

/**
 * This filter retrieves OAuth credential after user authenticates at the OAuth provider to create an OAuthToken to finish the OAuth
 * authentication process and retrieve the user profile. <br/>
 * 
 * https://github.com/scribejava/scribejava  <br/>
 * https://github.com/scribejava/scribejava/wiki/getting-started
 */
public final class Oauth2AuthenticatingFilter extends AbstractAuthenticatingFilter {
    
	private static final Logger LOG = LoggerFactory.getLogger(Oauth2AuthenticatingFilter.class);
	
    // the url where the application is redirected if the OAuth authentication fails
    private String failureUrl;
    
    // the OAuth20Service
    private OAuth20Service oauth20Service;;
    
    /**
     * HTTP Authorization Parameter, equal to <code>code</code>
     */
    protected static final String AUTHORIZATION_PARAMERTER = "code";
    
    private String authorizationParameterName = AUTHORIZATION_PARAMERTER;
    
    public Oauth2AuthenticatingFilter() {
    }
    
    /**
     * The token created for this authentication is an OAuthToken containing the OAuth credential received after authentication at the OAuth
     * provider. These information are received on the callback url (on which the filter must be configured).
     * 
     * @param request the incoming request
     * @param response the outgoing response
     */
	@Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
		try {
			OAuth2AccessToken accessToken = getOauth20Service().getAccessToken(getAuthzCode(request));
			LOG.debug("accessToken : {}", accessToken);
	        return new OAuth2Token(getHost(request), accessToken);
		} catch (IOException e) {
			throw new OAuth2AuthenticationException(e);
		} catch (InterruptedException e) {
			throw new OAuth2AuthenticationException(e);
		} catch (ExecutionException e) {
			throw new OAuth2AuthenticationException(e);
		}
    }
    
    /**
     * Execute login by creating {@link #createToken(javax.servlet.ServletRequest, javax.servlet.ServletResponse) token} and logging subject
     * with this token.
     * 
     * @param request the incoming request
     * @param response the outgoing response
     * @throws Exception if there is an error processing the request.
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
    	
    	Subject subject = getSubject(request, response);
		if ((null == subject || !subject.isAuthenticated()) && isOauth2Submission(request, response)) {
			AuthenticationToken token = createToken(request, response);
			try {
				subject.login(token);
				return true;
			} catch (AuthenticationException e) {
				LOG.error("Host {} Oauth2 Authentication Exception : {}", getHost(request), e.getMessage());
				saveRequestAndRedirectToLogin(request, response);
				return false;
			}
		}
		//如果用户没有身份验证，且没有auth code，则重定向到服务端授权    	
    	saveRequestAndRedirectToLogin(request, response);
		return false;
		
    }
    
    /**
     * Returns <code>false</code> to always force authentication (user is never considered authenticated by this filter).
     * 
     * @param request the incoming request
     * @param response the outgoing response
     * @param mappedValue the filter-specific config value mapped to this filter in the URL rules mappings.
     * @return <code>false</code>
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        return false;
    }
    
    /**
     * If login has been successful, redirect user to the original protected url.
     * 
     * @param token the token representing the current authentication
     * @param subject the current authenticated subjet
     * @param request the incoming request
     * @param response the outgoing response
     * @throws Exception if there is an error processing the request.
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
                                     ServletResponse response) throws Exception {
    	issueSuccessRedirect(request, response);
        return false;
    }
    
    /**
     * If login has failed, redirect user to the error page except if the user is already authenticated, in which case redirect to the
     * default success url.
     * 
     * @param token the token representing the current authentication
     * @param ae the current authentication exception
     * @param request the incoming request
     * @param response the outgoing response
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException ae, ServletRequest request,
                                     ServletResponse response) {
        // is user authenticated ?
        Subject subject = getSubject(request, response);
        if (subject.isAuthenticated() || subject.isRemembered()) {
			try {
				//如果身份验证成功了 则也重定向到成功页面  
				issueSuccessRedirect(request, response);
			} catch (Exception e) {
				LOG.error("Cannot redirect to the default success url", e);
			}
		} else {
			try {
				//登录失败时重定向到失败页面  
				WebUtils.issueRedirect(request, response, failureUrl);
			} catch (IOException e) {
				LOG.error("Cannot redirect to failure url : {}", failureUrl, e);
			}
		}
        return false;
    }
    
    @Override
    public String getLoginUrl() {
        return getOauth20Service().getAuthorizationUrl();
    }
    
    protected boolean isOauth2Submission(ServletRequest request, ServletResponse response) {
   	 String authzHeader = getAuthzCode(request);
		return (request instanceof HttpServletRequest) && authzHeader != null;
	}

   protected String getAuthzCode(ServletRequest request) {
       HttpServletRequest httpRequest = WebUtils.toHttp(request);
       return httpRequest.getParameter(getAuthorizationParameterName());
   }

    
    public void setFailureUrl(String failureUrl) {
        this.failureUrl = failureUrl;
    }

	public OAuth20Service getOauth20Service() {
		return oauth20Service;
	}

	public void setOauth20Service(OAuth20Service oauth20Service) {
		this.oauth20Service = oauth20Service;
	}

	public String getAuthorizationParameterName() {
		return authorizationParameterName;
	}

	public void setAuthorizationParameterName(String authorizationParameterName) {
		this.authorizationParameterName = authorizationParameterName;
	}
    
	
	
}
