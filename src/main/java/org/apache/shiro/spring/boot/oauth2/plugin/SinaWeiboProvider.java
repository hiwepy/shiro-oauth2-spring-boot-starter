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
package com.jeefw.cas.oauth.sina;

import org.pac4j.core.profile.UserProfile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

/**
 * *******************************************************************
 * @className	： SinaWeiboProvider
 * @description	： TODO(描述这个类的作用)
 * @author 		： <a href="mailto:hnxyhcwdl1003@163.com">wandalong</a>
 * @date		： Feb 28, 2016 8:49:40 PM
 * @version 	V1.0 
 * *******************************************************************
 */
public class SinaWeiboProvider extends BaseOAuth20Provider {
	  
	  @Override
	  protected void internalInit() {
	    if (scope != null) {
	      service = new ServiceBuilder().provider(SinaWeiboApi20.class).apiKey(key)
	          .apiSecret(secret).callback(callbackUrl).scope(scope).build();
	    } else {
	      service = new ServiceBuilder().provider(SinaWeiboApi20.class).apiKey(key)
	          .apiSecret(secret).callback(callbackUrl).build();
	    }
	    String[] names = new String[] {"uid", "username"};
	    for (String name : names) {
	      mainAttributes.put(name, null);
	    }
	    
	  }
	  
	  @Override
	  protected String getProfileUrl() {
	    return "https://api.weibo.com/2/statuses/user_timeline.json";
	  }
	  
	  @Override
	  protected UserProfile extractUserProfile(String body) {
	    UserProfile userProfile = new UserProfile();
	    JsonNode json = JsonHelper.getFirstNode(body);
	    ArrayNode statuses = (ArrayNode) json.get("statuses");
	    JsonNode userJson = statuses.get(0).get("user");
	    if (json != null) {
	      UserProfileHelper.addIdentifier(userProfile, userJson, "id");
	      for (String attribute : mainAttributes.keySet()) {
	        UserProfileHelper.addAttribute(userProfile, json, attribute,
	            mainAttributes.get(attribute));
	      }
	    }
	    JsonNode subJson = userJson.get("id");
	    if (subJson != null) {
	      UserProfileHelper
	          .addAttribute(userProfile, "uid", subJson.getIntValue());
	      
	    }
	    subJson = userJson.get("domain");
	    if (subJson != null) {
	      UserProfileHelper.addAttribute(userProfile, "username",
	          subJson.getTextValue());     
	    }

	    return userProfile;
	  }

	}
