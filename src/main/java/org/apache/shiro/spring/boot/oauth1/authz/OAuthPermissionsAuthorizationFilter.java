/*
 * Copyright (c) 1018, vindell (https://github.com/vindell).
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
package org.apache.shiro.spring.boot.oauth1.authz;



import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.apache.shiro.biz.web.filter.authz.PermissionsAuthorizationFilter;

import com.github.scribejava.core.model.OAuth1RequestToken;
import com.github.scribejava.core.oauth.OAuth10aService;

/**
 * This class specializes the PermissionsAuthorizationFilter to have a login url which is the authorization url of the OAuth provider.
 * https://github.com/scribejava/scribejava  <br/>
 * https://github.com/scribejava/scribejava/wiki/getting-started
 */
public final class OAuthPermissionsAuthorizationFilter extends PermissionsAuthorizationFilter {
    
	private OAuth10aService oauth10Service;
    
	@Override
    public String getLoginUrl() {
		try {
        	// Step 1: Get the request token
            OAuth1RequestToken requestToken = getOauth10Service().getRequestToken();
        	// Step 2: Making the user validate your request token
			return getOauth10Service().getAuthorizationUrl(requestToken);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
        return super.getLoginUrl();
    }
    
    public OAuth10aService getOauth10Service() {
		return oauth10Service;
	}

	public void setOauth10Service(OAuth10aService oauth10Service) {
		this.oauth10Service = oauth10Service;
	}
	
}
