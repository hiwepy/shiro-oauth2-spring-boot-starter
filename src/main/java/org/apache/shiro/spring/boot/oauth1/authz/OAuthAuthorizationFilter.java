package org.apache.shiro.spring.boot.oauth1.authz;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.biz.utils.WebUtils;
import org.apache.shiro.biz.web.filter.authz.AbstracAuthorizationFilter;
import org.apache.shiro.spring.boot.oauth1.token.OAuthToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;
import com.github.scribejava.core.oauth.OAuth10aService;

/**
 * OAuth授权 (authorization)过滤器 
 * @author 		： <a href="https://github.com/vindell">vindell</a>
 */
public final class OAuthAuthorizationFilter extends AbstracAuthorizationFilter {

	private static final Logger LOG = LoggerFactory.getLogger(OAuthAuthorizationFilter.class);
	 // the OAuth10aService
    private OAuth10aService oauth10Service;;
    
    /**
     * HTTP Authorization Parameter, equal to <code>code</code>
     */
    protected static final String AUTHORIZATION_PARAMERTER = "code";
    
    private String authorizationParameterName = AUTHORIZATION_PARAMERTER;
    
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		Subject subject = getSubject(request, response); 
		if ((null == subject || !subject.isAuthenticated()) && isOauthSubmission(request, response)) {
			
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
		
		// Step 1: Get the request token
		OAuth1RequestToken requestToken = getOauth10Service().getRequestToken();

		// Step 2: Get the access Token
		OAuth1AccessToken accessToken = getOauth10Service().getAccessToken(requestToken, getAuthzParameter(request));
		LOG.debug("accessToken : {}", accessToken);

		return new OAuthToken(WebUtils.getRemoteAddr(request), accessToken);
	}

	protected boolean isOauthSubmission(ServletRequest request, ServletResponse response) {
		String authzHeader = getAuthzParameter(request);
		return (request instanceof HttpServletRequest) && authzHeader != null;
	}

	protected String getAuthzParameter(ServletRequest request) {
		HttpServletRequest httpRequest = WebUtils.toHttp(request);
		return httpRequest.getParameter(getAuthorizationParameterName());
	}

    public OAuth10aService getOauth10Service() {
		return oauth10Service;
	}

	public void setOauth10Service(OAuth10aService oauth10Service) {
		this.oauth10Service = oauth10Service;
	}

	public String getAuthorizationParameterName() {
		return authorizationParameterName;
	}

	public void setAuthorizationParameterName(String authorizationParameterName) {
		this.authorizationParameterName = authorizationParameterName;
	}
    
}
