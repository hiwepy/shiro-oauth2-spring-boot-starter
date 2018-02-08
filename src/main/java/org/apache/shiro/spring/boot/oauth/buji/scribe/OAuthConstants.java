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
package org.apache.shiro.spring.boot.oauth.buji.scribe;

public class OAuthConstants extends org.scribe.model.OAuthConstants{

	public static final String QQ_ACCESS_TOKEN_URL = null;
	public static final String QQ_AUTHORIZE_URL = null;
	public static final String QQ_PROFILE_URL = null;
	
	public static final String SINA_AUTHORIZE_URL = "https://api.weibo.com/oauth2/authorize?client_id=%s&redirect_uri=%s&response_type=code";
	public static final String SINA_SCOPED_AUTHORIZE_URL = SINA_AUTHORIZE_URL + "&scope=%s";
	public static final String SINA_ACCESS_TOKEN_URL = "https://api.weibo.com/oauth2/access_token?grant_type=authorization_code";
	public static final String SINA_PROFILE_URL = "https://api.weibo.com/2/statuses/user_timeline.json";
	

}
