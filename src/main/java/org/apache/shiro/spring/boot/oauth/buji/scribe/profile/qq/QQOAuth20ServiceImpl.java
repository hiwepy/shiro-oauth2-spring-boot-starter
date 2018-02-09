package org.apache.shiro.spring.boot.oauth.buji.scribe.profile.qq;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.scribe.builder.api.DefaultApi20;
import org.scribe.exceptions.OAuthException;
import org.scribe.model.OAuthConfig;
import org.scribe.model.OAuthConstants;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.up.addon_to_scribe.ProxyOAuth20ServiceImpl;

public class QQOAuth20ServiceImpl extends ProxyOAuth20ServiceImpl {

	private static Pattern openIdPattern = Pattern.compile("\"openid\":\\s*\"(\\S*?)\"");

	public QQOAuth20ServiceImpl(DefaultApi20 api, OAuthConfig config, String proxyHost, int proxyPort) {
		super(api, config, proxyHost, proxyPort);
	}

	@Override
	public Token getAccessToken(final Token requestToken, final Verifier verifier) {
		final OAuthRequest request = new OAuthRequest(this.api.getAccessTokenVerb(), this.api.getAccessTokenEndpoint());
		request.addBodyParameter("client_id", this.config.getApiKey());
		request.addBodyParameter("client_secret", this.config.getApiSecret());
		request.addBodyParameter(OAuthConstants.CODE, verifier.getValue());
		request.addBodyParameter(OAuthConstants.REDIRECT_URI, this.config.getCallback());
		request.addBodyParameter("grant_type", "authorization_code");
		final Response response = request.send();
		return this.api.getAccessTokenExtractor().extract(response.getBody());
	}

	@Override
	public void signRequest(final Token accessToken, final OAuthRequest request) {
		request.addQuerystringParameter(OAuthConstants.ACCESS_TOKEN, accessToken.getToken());
		String response = accessToken.getRawResponse();
		Matcher matcher = openIdPattern.matcher(response);
		if (matcher.find()) {
			request.addQuerystringParameter("openid", matcher.group(1));
		} else {
			throw new OAuthException("接口返回数据miss openid: " + response);
		}
	}
}
