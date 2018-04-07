/*
 * Copyright (c) 2017, vindell (https://github.com/vindell).
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
package org.apache.shiro.spring.boot.oauth.buji.scribe.provider;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.shiro.spring.boot.oauth.buji.scribe.OAuth2Constants;
import org.apache.shiro.spring.boot.oauth.buji.scribe.api.QQApi20;
import org.apache.shiro.spring.boot.oauth.buji.scribe.profile.qq.QQAttributesDefinition;
import org.apache.shiro.spring.boot.oauth.buji.scribe.profile.qq.QQOAuth20ServiceImpl;
import org.apache.shiro.spring.boot.oauth.buji.scribe.profile.qq.QQProfile;
import org.scribe.model.OAuthConfig;
import org.scribe.model.OAuthConstants;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.SignatureType;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.up.credential.OAuthCredential;
import org.scribe.up.profile.JsonHelper;
import org.scribe.up.provider.BaseOAuth20Provider;
import org.scribe.up.provider.BaseOAuthProvider;
import org.scribe.up.provider.exception.HttpException;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * http://wiki.open.qq.com/wiki/%E3%80%90QQ%E7%99%BB%E5%BD%95%E3%80%91%E5%BC%80%E5%8F%91%E6%94%BB%E7%95%A5_Client-side
 */
public final class QQProvider extends BaseOAuth20Provider {
    
	private static Pattern openIdPattern = Pattern.compile("\"openid\":\\s*\"(\\S*?)\"");
	private static final String PROFILE_URL = "https://graph.qq.com/user/get_user_info";
	private static final String OPENID_URL = "https://graph.qq.com/oauth2.0/me?access_token=%s";
	
	private final static QQAttributesDefinition QQ_ATTRIBUTES = new QQAttributesDefinition();
	
    @Override
    protected void internalInit() {
       
    	/*this.service = new ServiceBuilder().provider(QQApi20.class).apiKey(this.key).apiSecret(this.secret)
            .callback(this.callbackUrl).build();*/
    	QQApi20 api = new QQApi20();
        this.service = new QQOAuth20ServiceImpl(api, new OAuthConfig(this.key, this.secret, this.callbackUrl, SignatureType.Header, null, null),
                this.proxyHost,this.proxyPort);
        
    }
    
	/**
	 * Step1：获取Authorization Code
	 * 此不操作交由 {@link org.apache.shiro.spring.boot.oauth.buji.scribe.profile.qq.QQOAuth20ServiceImpl#getAccessToken(Token, org.scribe.model.Verifier)}
	 */
	@Override
	protected Token getAccessToken(OAuthCredential credential) {
		return super.getAccessToken(credential);
	}

    @Override
    protected String sendRequestForData(final Token accessToken, final String dataUrl) throws HttpException {
    	
    	logger.debug("accessToken : {} / dataUrl : {}", accessToken, dataUrl);
        final long t0 = System.currentTimeMillis();
        
    	// Step3：使用Access Token来获取用户的OpenID
    	
    	//  1.发送请求到如下地址（请将access_token等参数值替换为你自己的）： https://graph.qq.com/oauth2.0/me?access_token=YOUR_ACCESS_TOKEN
        String getOpenIdUrl = String.format(OPENID_URL, accessToken.getToken());
        OAuthRequest request = new OAuthRequest(Verb.GET, getOpenIdUrl);
        Response response = request.send();
        int code = response.getCode();
        String body = response.getBody();
        if (code != 200) {
        	logger.error("Failed to get OpenID, code : " + code + " / body : " + body);
            throw new HttpException(code, body);
        }
        // 2. 获取到用户OpenID，返回包如下： callback( {"client_id":"YOUR_APPID","openid":"YOUR_OPENID"} ); 
        Matcher matcher = openIdPattern.matcher(body);
        String openid = "";
        if(matcher.find()){

        	// Step4：使用Access Token以及OpenID来访问和修改用户数据
        	
        	openid = matcher.group(1);
        	 
        	// （1）发送请求到get_user_info的URL（请将access_token，appid等参数值替换为你自己的）：
        	// https://graph.qq.com/user/get_user_info?access_token=YOUR_ACCESS_TOKEN&oauth_consumer_key=YOUR_APP_ID&openid=YOUR_OPENID
        	request = new OAuthRequest(Verb.GET, dataUrl);
            request.addQuerystringParameter(OAuthConstants.ACCESS_TOKEN, accessToken.getToken());
            request.addQuerystringParameter(OAuth2Constants.OAUTH_CONSUMER_KEY, this.getKey());
            request.addQuerystringParameter(OAuth2Constants.OPENID, openid);
            // （2）成功返回后，即可获取到用户数据：
            response = request.send();
            code = response.getCode();
            body = response.getBody();
            final long t1 = System.currentTimeMillis();
            logger.debug("Request took : " + (t1 - t0) + " ms for : " + dataUrl);
            logger.debug("response code : {} / response body : {}", code, body);
            if (code != 200) {
                logger.error("Failed to get data, code : " + code + " / body : " + body);
                throw new HttpException(code, body);
            }
            JSONObject json = JSONObject.parseObject(body);
            json.put(OAuth2Constants.OPENID, openid);
            json.put(OAuthConstants.ACCESS_TOKEN, accessToken.getToken());
            return json.toString();
        }
        logger.error("Failed to get OpenID, code : " + code + " / body : " + body);
        throw new HttpException(code, body);
    }
    
    @Override
    protected String getProfileUrl() {
        return PROFILE_URL;
    }
    
	@Override
    protected QQProfile extractUserProfile(final String body) {
        QQProfile profile = new QQProfile();
        final JsonNode json = JsonHelper.getFirstNode(body);
        if (null != json) {
        	
        	profile.setId(JsonHelper.get(json, OAuth2Constants.OPENID));
        	profile.setAccessToken(String.valueOf(JsonHelper.get(json, OAuthConstants.ACCESS_TOKEN)));
        	
            for(final String attribute : QQ_ATTRIBUTES.getPrincipalAttributes()){
                profile.addAttribute(attribute, JsonHelper.get(json, attribute));
                if(attribute.equals("headimgurl")) {
                    profile.addAttribute(attribute, JsonHelper.get(json, "figureurl_qq_1"));
                }
            }
            
        }
        return profile;
    }
	
    
    @Override
    protected BaseOAuthProvider newProvider() {
        return new QQProvider();
    }
    
}