/*
 * Copyright (c) 2010-2020, wandalong (hnxyhcwdl1003@163.com).
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

import org.apache.shiro.spring.boot.oauth.buji.scribe.profile.sina.SinaAttributesDefinition;
import org.apache.shiro.spring.boot.oauth.buji.scribe.profile.sina.SinaProfile;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.SinaWeiboApi20;
import org.scribe.up.profile.AttributesDefinition;
import org.scribe.up.profile.JsonHelper;
import org.scribe.up.profile.UserProfile;
import org.scribe.up.provider.BaseOAuth20Provider;
import org.scribe.up.provider.BaseOAuthProvider;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * http://open.weibo.com/wiki/2/statuses/user_timeline
 */
public final class SinaWeiboProvider extends BaseOAuth20Provider {
    
	private final static AttributesDefinition SINA_ATTRIBUTES = new SinaAttributesDefinition();
	private final static String PROFILE_URL = "https://api.weibo.com/2/statuses/user_timeline.json";
	 
    @Override
    protected void internalInit() {
        this.service = new ServiceBuilder().provider(SinaWeiboApi20.class).apiKey(this.key).apiSecret(this.secret)
            .callback(this.callbackUrl).build();
    }
    
    @Override
    protected String getProfileUrl() {
        return PROFILE_URL;
    }
    
    @Override
    protected UserProfile extractUserProfile(final String body) {
    	final SinaProfile profile = new SinaProfile();
        JsonNode json = JsonHelper.getFirstNode(body);
        if (json != null) {
            profile.setId(JsonHelper.get(json, "ID"));
            for (final String attribute : SINA_ATTRIBUTES.getPrincipalAttributes()) {
                profile.addAttribute(attribute, JsonHelper.get(json, attribute));
            }
           /* json = json.get("meta");
            if (json != null) {
                final String attribute = OAuthAttributesDefinitions.LINKS;
                profile.addAttribute(attribute, JsonHelper.get(json, attribute));
            }*/
        }
        return profile;
    }
    
    @Override
    protected BaseOAuthProvider newProvider() {
        return new SinaWeiboProvider();
    }
    
}