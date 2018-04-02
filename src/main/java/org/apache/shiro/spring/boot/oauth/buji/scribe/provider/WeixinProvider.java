/*
 * Copyright (c) 2010-2020, vindell (hnxyhcwdl1003@163.com).
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

import java.util.concurrent.TimeUnit;

import org.apache.shiro.spring.boot.oauth.buji.scribe.api.WeiXinApi20;
import org.apache.shiro.spring.boot.oauth.buji.scribe.profile.weixin.WeiXinAttributesDefinition;
import org.apache.shiro.spring.boot.oauth.buji.scribe.profile.weixin.WeiXinOAuth20ServiceImpl;
import org.apache.shiro.spring.boot.oauth.buji.scribe.profile.weixin.WeiXinProfile;
import org.scribe.model.OAuthConfig;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.SignatureType;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.up.profile.JsonHelper;
import org.scribe.up.provider.BaseOAuth20Provider;
import org.scribe.up.provider.BaseOAuthProvider;
import org.scribe.up.provider.exception.HttpException;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * This class is the OAuth provider to authenticate user in CAS server wrapping OAuth protocol.
 * 
 * @author Jerome Leleu
 * @since 3.5.0
 */
public final class WeixinProvider extends BaseOAuth20Provider {
    
	private static final String PROFILE_URL = "https://api.weixin.qq.com/sns/userinfo";
	private final static WeiXinAttributesDefinition WEI_XIN_ATTRIBUTES = new WeiXinAttributesDefinition();

    @Override
    protected void internalInit() {
    	
    	/*this.service = new ServiceBuilder().provider(WeiXinApi20.class).apiKey(this.key).apiSecret(this.secret)
            .callback(this.callbackUrl).build();*/
        
        WeiXinApi20 api = new WeiXinApi20();
        this.service = new WeiXinOAuth20ServiceImpl(api, new OAuthConfig(this.key, this.secret, this.callbackUrl, SignatureType.Header, null, null),
                this.proxyHost, this.proxyPort);
    }
    
    @Override
    protected String getProfileUrl() {
        return PROFILE_URL;
    }
    
    protected String getProfileUrl(Token accessToken) {
        return "https://api.weixin.qq.com/sns/userinfo?access_token="+accessToken.getToken()+"&scope=snsapi_userinfo";
    }
    
    @Override
    protected WeiXinProfile extractUserProfile(String body) {
        WeiXinProfile weiXinProfile = new WeiXinProfile();
        final JsonNode json = JsonHelper.getFirstNode(body);
        if (null != json) {
            for(final String attribute : WEI_XIN_ATTRIBUTES.getPrincipalAttributes()){
                weiXinProfile.addAttribute(attribute, JsonHelper.get(json, attribute));
            }
            /** 绑定账号到系统 */
            String openId = (String) weiXinProfile.getAttributes().get("openid");
            String nickName = (String) weiXinProfile.getAttributes().get("nickname");
           /* String userName = getUserNameByOpenId(openId);
            if (StringUtils.isNotBlank(userName)) {
                weiXinProfile.setId(userName);
            }*/
        }
        return weiXinProfile;
    }
    
    @Override
    protected String sendRequestForData(Token accessToken, String dataUrl) throws HttpException {
    	logger.debug("accessToken : {} / dataUrl : {}", accessToken, dataUrl);
        final long t0 = System.currentTimeMillis();
        final OAuthRequest request = new OAuthRequest(Verb.GET, dataUrl);
        if (this.connectTimeout != 0) {
            request.setConnectTimeout(this.connectTimeout, TimeUnit.MILLISECONDS);
        }
        if (this.readTimeout != 0) {
            request.setReadTimeout(this.readTimeout, TimeUnit.MILLISECONDS);
        }
        this.service.signRequest(accessToken, request);
        final Response response = request.send();
        final int code = response.getCode();
        final String body = response.getBody();
        final long t1 = System.currentTimeMillis();
        logger.debug("Request took : " + (t1 - t0) + " ms for : " + dataUrl);
        logger.debug("response code : {} / response body : {}", code, body);
        if (code != 200) {
        	logger.error("Failed to get user data, code : " + code + " / body : " + body);
            throw new HttpException(code, body);
        }
        return body;
    }
    
    @Override
    protected BaseOAuthProvider newProvider() {
        return new WeixinProvider();
    }
    
}