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
package org.apache.shiro.spring.boot.oauth.exception;


import org.apache.shiro.authc.AuthenticationException;

/**
 * This class is the exception that is thrown when OAuth authentication process fails in OAuthRealm.
 * 
 * @author Jerome Leleu
 * @since 1.0.0
 */
public final class OAuthAuthenticationException extends AuthenticationException {
    
    private static final long serialVersionUID = -9060319148695558222L;
    
    public OAuthAuthenticationException() {
        super();
    }
    
    public OAuthAuthenticationException(String message) {
        super(message);
    }
    
    public OAuthAuthenticationException(Throwable cause) {
        super(cause);
    }
    
    public OAuthAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
