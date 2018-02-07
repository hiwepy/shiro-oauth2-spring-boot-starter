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
package org.apache.shiro.spring.boot.oauth2.plugin;

import org.scribe.builder.api.DefaultApi20;
import org.scribe.model.OAuthConfig;
import org.scribe.utils.OAuthEncoder;
public class QQApi20 extends DefaultApi20 {
	
    private static String serverUrl = "";
    
    @Override
    public String getAccessTokenEndpoint() {
        return serverUrl + "/" + OAuthConstants.QQ_ACCESS_TOKEN_URL + "?grant_type=authorization_code";
    }
    
    @Override
    public String getAuthorizationUrl(final OAuthConfig config) {
        return String.format(serverUrl + "/" + OAuthConstants.QQ_AUTHORIZE_URL  + "?client_id=%s&redirect_uri=%s&response_type=code", config.getApiKey(),
        OAuthEncoder.encode(config.getCallback()));
    }
    
    public static void setServerUrl(final String url) {
        serverUrl = url;
    }
}