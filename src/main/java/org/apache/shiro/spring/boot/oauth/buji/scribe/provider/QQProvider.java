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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.shiro.spring.boot.oauth.buji.scribe.OAuthConstants;
import org.apache.shiro.spring.boot.oauth.buji.scribe.api.QQApi20;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.Token;
import org.scribe.up.profile.JsonHelper;
import org.scribe.up.profile.UserProfile;
import org.scribe.up.provider.BaseOAuth20Provider;
import org.scribe.up.provider.BaseOAuthProvider;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * This class is the OAuth provider to authenticate user in CAS server wrapping OAuth protocol.
 * 
 * @author Jerome Leleu
 * @since 3.5.0
 */
public final class QQProvider extends BaseOAuth20Provider {
    
    
    @Override
    protected void internalInit() {
        this.service = new ServiceBuilder().provider(QQApi20.class).apiKey(this.key).apiSecret(this.secret)
            .callback(this.callbackUrl).build();
    }
    
    @Override
    protected String getProfileUrl() {
        return OAuthConstants.QQ_PROFILE_URL;
    }
    
	@Override
    protected UserProfile extractUserProfile(final String body) {
    	
        final QQWrapperProfile userProfile = new QQWrapperProfile();
        String str = body.replace("callback( ", "").replace(" );", "");
        JsonNode json = JsonHelper.getFirstNode(str);
		userProfile.setId(JsonHelper.get(json, QQWrapperProfile.ID));
		userProfile.addAttribute(QQWrapperProfile.CLIENTID, JsonHelper.get(json, QQWrapperProfile.CLIENTID));
        return userProfile;
    }
	
	private static String getRandomStr(){
		SimpleDateFormat formatter = new SimpleDateFormat ("MMddHHmmss");
		Date curDate = new Date(System.currentTimeMillis());//获取当前时间
		String str = formatter.format(curDate);
		String cur = str.substring(0,2);
		String cur2 = str.substring(2,4);
		String temp = (Integer.parseInt(cur)+Integer.parseInt(cur2))+""+str.substring(4);
		int cur_id = Integer.parseInt(temp.substring(0,4))+Integer.parseInt(temp.substring(4));
		String randomstr ="y" + cur_id + (int)(Math.random()*10000);
		return randomstr;
	}
   
	@SuppressWarnings({ "deprecation", "rawtypes" })
    @Override
    protected UserProfile getUserProfile(final Token accessToken) {
        final String body = sendRequestForData(accessToken, getProfileUrl());
        if (body == null) {
            return null;
        }
        final UserProfile profile = extractUserProfile(body);
        addAccessTokenToProfile(profile, accessToken);
        String url = "https://graph.qq.com/user/get_user_info?access_token="+accessToken.getToken()+"&oauth_consumer_key="+this.key+"&openid="+profile.getId();
        String response = getHttp(url);
        JsonNode json = JsonHelper.getFirstNode(response);
        String ret = json.get("ret").asText();
        if(ret != null && ret.equals("0")){
        	List list = jpaTemplate.find("from UserInfo where openid='"+profile.getId()+"'");
    		UserInfo userInfo = null;
    		if(list == null || list.isEmpty()){
    			userInfo = new UserInfo();
    			String nickName = json.get("nickname").asText();
    			nickName = nickName.replaceAll("[^0-9a-zA-Z\\u4e00-\\u9fa5]", "");
    			userInfo.setNickName(nickName);
    			userInfo.setAvatar(json.get("figureurl_qq_1").asText());
    			userInfo.setGender(json.get("gender").asText());
    			userInfo.setOpenId(profile.getId());
    			userInfo.setClientId((String)profile.getAttributes().get(QQWrapperProfile.CLIENTID));
    			userInfo.setUserName(getRandomStr());
    			userInfo.setCompleted(0);
    			if(userInfo.getNickName() == null || userInfo.getNickName().equals("")){
    				userInfo.setNickName(userInfo.getUserName());
    			}
    			userInfo.setCreateTime(new Date());
    			jpaTemplate.persist(userInfo);
    		}else{
    			userInfo = (UserInfo) list.get(0);
    			userInfo.setUpdateTime(new Date());
    			jpaTemplate.merge(userInfo);
    		}
    		profile.setId(userInfo.getUserName());
    		profile.addAttribute("userId",userInfo.getUserId());
    		profile.addAttribute("userName",userInfo.getUserName());
            profile.addAttribute("nickName",userInfo.getNickName());
            profile.addAttribute("avatar",userInfo.getAvatar());
            profile.addAttribute("gender",userInfo.getGender());
            profile.addAttribute("openId",userInfo.getOpenId());
            profile.addAttribute("clientId",userInfo.getClientId());
            profile.addAttribute("completed",userInfo.getCompleted());
        }
        return profile;
    }
    
    public static String getHttp(String url) {
		String responseMsg = "";
		HttpClient httpClient = new HttpClient();
		GetMethod getMethod = new GetMethod(url);
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler());
		try {
			httpClient.executeMethod(getMethod);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			InputStream in = getMethod.getResponseBodyAsStream();
			int len = 0;
			byte[] buf = new byte[1024];
			while((len=in.read(buf))!=-1){
				out.write(buf, 0, len);
			}
			responseMsg = out.toString("UTF-8");
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			//释放连接
			getMethod.releaseConnection();
		}
		return responseMsg;
	}
    
    public void setServerUrl(final String serverUrl) {
        this.serverUrl = serverUrl;
    }
    
    @Override
    protected BaseOAuthProvider newProvider() {
        final QQProvider newProvider = new QQProvider();
        newProvider.setServerUrl(this.serverUrl);
        return newProvider;
    }
 
    
}