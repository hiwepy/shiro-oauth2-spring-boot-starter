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
package org.apache.shiro.spring.boot.oauth;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This implementation uses the Shiro session for the user session.
 * 
 * @author Jerome Leleu
 * @since 1.0.0
 */
public final class ShiroUserSession implements UserSession {
    
    private static Logger log = LoggerFactory.getLogger(OAuth2Filter.class);
    
    public void setAttribute(String key, Object value) {
        // TODO : find a better solution
        try {
            SecurityUtils.getSubject().getSession().setAttribute(key, value);
        } catch (UnavailableSecurityManagerException e) {
            log.warn("Should happen just once at startup in some specific case of Shiro Spring configuration", e);
        }
    }
    
    public Object getAttribute(String key) {
        return SecurityUtils.getSubject().getSession().getAttribute(key);
    }
}
