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
package org.apache.shiro.spring.boot.oauth2.token;


import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.HostAuthenticationToken;

import com.github.scribejava.core.model.OAuth2AccessToken;

/**
 * This class represents a token for an OAuth authentication process (OAuth credential + user identifier after authentication).
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
public final class OAuth2Token implements AuthenticationToken, HostAuthenticationToken {
    
    private static final long serialVersionUID = 3376624432421737333L;
    
    // 客户端IP
 	private String host;
 	
    private OAuth2AccessToken credential;
    
    private String userId;
    
    public OAuth2Token(String host, OAuth2AccessToken credential) {
    	this.host = host;
        this.credential = credential;
    }
    
    @Override
	/**
	 * Returns the host.
	 *
	 * @return the host
	 */
	public String getHost() {
		return host;
	}
    
    /**
     * Sets the user id.
     *
     * @param userId the user id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    /**
     * Returns the principal.
     *
     * @return the principal
     */
    public Object getPrincipal() {
        return userId;
    }
    
    /**
     * Returns the credentials.
     *
     * @return the credentials
     */
    public Object getCredentials() {
        return credential;
    }
}

