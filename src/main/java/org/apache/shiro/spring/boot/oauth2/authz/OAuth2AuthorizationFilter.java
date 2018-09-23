package org.apache.shiro.spring.boot.oauth2.authz;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.biz.utils.WebUtils;
import org.apache.shiro.biz.web.filter.authz.AbstracAuthorizationFilter;
import org.apache.shiro.spring.boot.oauth2.exception.OAuth2AuthenticationException;
import org.apache.shiro.spring.boot.oauth2.token.OAuth2Token;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.oauth.OAuth20Service;

/**
 * OAuth授权 (authorization)过滤器
 * 
 * @author ： <a href="https://github.com/vindell">vindell</a>
 */
public final class OAuth2AuthorizationFilter extends AbstracAuthorizationFilter {

	private static final Logger LOG = LoggerFactory.getLogger(OAuth2AuthorizationFilter.class);
	// the OAuth20Service
	private OAuth20Service oauth20Service;

	/**
	 * HTTP Authorization Parameter, equal to <code>code</code>
	 */
	protected static final String AUTHORIZATION_PARAMERTER = "code";

	private String authorizationParameterName = AUTHORIZATION_PARAMERTER;

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		Subject subject = getSubject(request, response);
		if ((null == subject || !subject.isAuthenticated()) && isOauth2Submission(request, response)) {

			AuthenticationToken token = createToken(request, response);
			try {
				subject = getSubject(request, response);
				subject.login(token);
				return true;
			} catch (AuthenticationException e) {
				LOG.error("Host {} JWT Authentication Exception : {}", WebUtils.getRemoteAddr(request), e.getMessage());
				return false;
			}
		}
		return false;
	}

	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
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

	protected boolean isOauth2Submission(ServletRequest request, ServletResponse response) {
		String authzHeader = getAuthzCode(request);
		return (request instanceof HttpServletRequest) && authzHeader != null;
	}

	protected String getAuthzCode(ServletRequest request) {
		HttpServletRequest httpRequest = WebUtils.toHttp(request);
		return httpRequest.getParameter(getAuthorizationParameterName());
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
