package org.apache.shiro.spring.boot.oauth.buji.scribe.api;

import org.scribe.builder.api.DefaultApi20;
import org.scribe.model.OAuthConfig;
import org.scribe.model.Verb;
import org.scribe.utils.OAuthEncoder;

public class QQApi20 extends DefaultApi20 {

	private static final String AUTHORIZE_URL = "https://graph.qq.com/oauth2.0/authorize?client_id=%s&redirect_uri=%s&response_type=code&state=test";
	private static final String ACCESS_TOKEN_URL = "https://graph.qq.com/oauth2.0/token?grant_type=authorization_code";
	
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
	
}
