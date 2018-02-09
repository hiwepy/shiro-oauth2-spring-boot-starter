/*
 * Copyright (c) 2010-2020, vindell (https://github.com/vindell).
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.shiro.spring.boot.oauth.buji.scribe.api.QQApi20;
import org.apache.shiro.spring.boot.oauth.buji.scribe.profile.qq.QQAttributesDefinition;
import org.apache.shiro.spring.boot.oauth.buji.scribe.profile.qq.QQOAuth20ServiceImpl;
import org.apache.shiro.spring.boot.oauth.buji.scribe.profile.qq.QQProfile;
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

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * This class is the OAuth provider to authenticate user in CAS server wrapping OAuth protocol.
 * 
 * @author Jerome Leleu
 * @since 3.5.0
 */
public final class QQProvider extends BaseOAuth20Provider {
    
	private static Pattern openIdPattern = Pattern.compile("\"openid\":\\s*\"(\\S*?)\"");
	private static final String PROFILE_URL = "https://graph.qq.com/user/get_user_info";
	private final static QQAttributesDefinition QQ_ATTRIBUTES = new QQAttributesDefinition();
	
    @Override
    protected void internalInit() {
       
    	/*this.service = new ServiceBuilder().provider(QQApi20.class).apiKey(this.key).apiSecret(this.secret)
            .callback(this.callbackUrl).build();*/
    	QQApi20 api = new QQApi20();
        this.service = new QQOAuth20ServiceImpl(api, new OAuthConfig(this.key, this.secret, this.callbackUrl, SignatureType.Header, null, null),
                this.proxyHost,this.proxyPort);
        
    }
    
    @Override
    protected String getProfileUrl() {
        return PROFILE_URL;
    }
    
    protected String getProfileUrl(Token token) {
        return PROFILE_URL + "?access_token="+token.getToken()+"&oauth_consumer_key="+this.key+"";
    }
    
	@Override
    protected QQProfile extractUserProfile(final String body) {
        QQProfile profile = new QQProfile();
        String str = body.replace("callback( ", "").replace(" );", "");
        final JsonNode json = JsonHelper.getFirstNode(str);
        if (null != json) {
        	
        	profile.setId(JsonHelper.get(json, QQProfile.ID));
        	profile.addAttribute(QQProfile.CLIENTID, JsonHelper.get(json, QQProfile.CLIENTID));
    		
            for(final String attribute : QQ_ATTRIBUTES.getPrincipalAttributes()){
                profile.addAttribute(attribute, JsonHelper.get(json, attribute));
                if(attribute.equals("headimgurl")) {
                    profile.addAttribute(attribute, JsonHelper.get(json, "figureurl_qq_1"));
                }
            }
            /**
             * 绑定QQ账号
             */
            String openId = (String) profile.getAttributes().get("openid");
            String nickName = (String) profile.getAttributes().get("nickname");
            /*String userName = getUserNameByOpenId(openId);
            if (StringUtils.isNotBlank(userName)) {
                profile.setId(userName);
            }*/
        }
        return profile;
    }

    @Override
    protected String sendRequestForData(final Token accessToken, final String dataUrl) throws HttpException {
        String getOpenIdUrl = "https://graph.qq.com/oauth2.0/me?access_token="+accessToken.getToken()+"";
        OAuthRequest request = new OAuthRequest(Verb.GET,getOpenIdUrl);
        if (this.connectTimeout != 0) {
            request.setConnectTimeout(this.connectTimeout, TimeUnit.MILLISECONDS);
        }
        if (this.readTimeout != 0) {
            request.setReadTimeout(this.readTimeout, TimeUnit.MILLISECONDS);
        }
        Response response = request.send();
        String body = response.getBody();
        Matcher matcher = openIdPattern.matcher(body);
        String openid = "";
        if(matcher.find()){
            request = new OAuthRequest(Verb.GET,dataUrl);
            openid = matcher.group(1);
            request.addQuerystringParameter("openid", matcher.group(1));
        }
        response = request.send();
        int code = response.getCode();
        body = response.getBody();
        if (code != 200) {
            logger.error("Failed to get data, code : " + code + " / body : " + body);
            throw new HttpException(code, body);
        }
        JSONObject json = JSONObject.parseObject(body);
        json.put("openid",openid);
        return json.toString();
    }
    
    @Override
    protected BaseOAuthProvider newProvider() {
        return new QQProvider();
    }
    
}