package org.apache.shiro.spring.boot.oauth.buji.scribe.api;

import org.apache.shiro.spring.boot.oauth.buji.scribe.profile.weixin.WeiXinOAuth20ServiceImpl;
import org.scribe.builder.api.DefaultApi20;
import org.scribe.extractors.AccessTokenExtractor;
import org.scribe.extractors.JsonTokenExtractor;
import org.scribe.model.OAuthConfig;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;
import org.scribe.utils.OAuthEncoder;

/**
 * 用于定义获取微信返回的CODE与ACCESS_TOKEN
 */
public class WeiXinApi20 extends DefaultApi20 {

    private static final String AUTHORIZE_URL = "https://open.weixin.qq.com/connect/qrconnect?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_login#wechat_redirect";
    private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
    
    String proxyHost; 
    int proxyPort;
    
    @Override
    public OAuthService createService(OAuthConfig config) {
    	return new WeiXinOAuth20ServiceImpl(this, config, proxyHost, proxyPort);
    }
    
    @Override
    public Verb getAccessTokenVerb() {
      return Verb.POST;
    }

    @Override
    public String getAccessTokenEndpoint() {
        return ACCESS_TOKEN_URL;
    }

    @Override
    public String getAuthorizationUrl(OAuthConfig config) {
        return String.format(AUTHORIZE_URL, config.getApiKey(), OAuthEncoder.encode(config.getCallback()));
    }
    
    @Override
	public AccessTokenExtractor getAccessTokenExtractor() {
		return new JsonTokenExtractor();
	}

}
