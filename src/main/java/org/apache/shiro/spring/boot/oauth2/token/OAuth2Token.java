/*
 * Copyright (c) 2018, vindell (https://github.com/vindell).
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

import com.github.scribejava.core.model.OAuth2AccessToken;

/**
 * This class represents a token for an OAuth authentication process (OAuth credential + user identifier after authentication).
 */
public final class OAuth2Token implements AuthenticationToken {
    
    private static final long serialVersionUID = 3376624432421737333L;
    
    private OAuth2AccessToken credential;
    
    private String userId;
    
    public OAuth2Token(OAuth2AccessToken credential) {
        this.credential = credential;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public Object getPrincipal() {
        return userId;
    }
    
    public Object getCredentials() {
        return credential;
    }
}

