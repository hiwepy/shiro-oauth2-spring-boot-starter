/*
 * Copyright (c) 2018, hiwepy (https://github.com/easy-4-java).
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
package org.apache.shiro.spring.boot.oauth2.authz;



import org.apache.shiro.biz.web.filter.authz.PermissionsAuthorizationFilter;

import com.github.scribejava.core.oauth.OAuth20Service;

/**
 * This class specializes the PermissionsAuthorizationFilter to have a login url which is the authorization url of the OAuth provider.
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
public final class OAuth2PermissionsAuthorizationFilter extends PermissionsAuthorizationFilter {
    
	private OAuth20Service oauth20Service;
    
	@Override
    /**
     * Returns the login url.
     *
     * @return the login url
     */
    public String getLoginUrl() {
        return getOauth20Service().getAuthorizationUrl();
    }
    
    /**
     * Returns the oauth20 service.
     *
     * @return the oauth20 service
     */
    public OAuth20Service getOauth20Service() {
		return oauth20Service;
	}

	/**
	 * Sets the oauth20 service.
	 *
	 * @param oauth20Service the oauth20 service
	 */
	public void setOauth20Service(OAuth20Service oauth20Service) {
		this.oauth20Service = oauth20Service;
	}
	
}
