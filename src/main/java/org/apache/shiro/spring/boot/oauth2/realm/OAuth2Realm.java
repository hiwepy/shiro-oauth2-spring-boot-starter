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
package org.apache.shiro.spring.boot.oauth2.realm;


import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.boot.oauth.exception.OAuthAuthenticationException;
import org.apache.shiro.spring.boot.oauth.scribejava.token.OAuth2Token;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.oauth.OAuth20Service;

/**
 * This realm implementation is dedicated to OAuth authentication. It acts on OAuth credential after user authenticates at the OAuth
 * provider (Facebook, Twitter...) and finishes the OAuth authentication process by getting the user profile from the OAuth provider.
 * 
 * @author Jerome Leleu
 * @since 1.0.0
 */
public class OAuth2Realm extends AuthorizingRealm {
    
    private static Logger log = LoggerFactory.getLogger(OAuth2Realm.class);
    
    // the OAuth20Service
    private OAuth20Service oauth20Service;;
    
    // default roles applied to authenticated user
    private String defaultRoles;
    
    // default permissions applied to authenticated user
    private String defaultPermissions;
    
    public OAuth2Realm() {
        setAuthenticationTokenClass(OAuth2Token.class);
    }
    
    /**
     * Authenticates a user and retrieves its user profile.
     * 
     * @param authenticationToken the authentication token
     * @throws AuthenticationException if there is an error during authentication.
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(final AuthenticationToken authenticationToken)
        throws AuthenticationException {
        final OAuth2Token oauthToken = (OAuth2Token) authenticationToken;
        log.debug("oauthToken : {}", oauthToken);
        // token must be provided
        if (oauthToken == null) {
            return null;
        }
        
        // OAuth credential
        final OAuth2AccessToken credential = (OAuth2AccessToken) oauthToken.getCredentials();
        log.debug("credential : {}", credential);
        // credential should be not null
        if (credential == null) {
            return null;
        }

        // OAuth provider
        OAuthProvider provider = providersDefinition.findProvider(credential.getProviderType());
        log.debug("provider : {}", provider);
        // no provider found
        if (provider == null) {
            return null;
        }
        
        // finish OAuth authentication process : get the user profile
         UserProfile userProfile = provider.getUserProfile(credential);
        log.debug("userProfile : {}", userProfile);
        if (userProfile == null || !StringUtils.hasText(userProfile.getId())) {
            log.error("Unable to get user profile for OAuth credentials : [{}]", credential);
            throw new OAuthAuthenticationException("Unable to get user profile for OAuth credential : [" + credential
                                                   + "]");
        }
        
        // refresh authentication token with user id
        final String userId = userProfile.getTypedId();
        oauthToken.setUserId(userId);
        // create simple authentication info
        final List<? extends Object> principals = CollectionUtils.asList(userId, userProfile);
        final PrincipalCollection principalCollection = new SimplePrincipalCollection(principals, getName());
        return new SimpleAuthenticationInfo(principalCollection, credential);
    }
    
    /**
     * Retrieves the AuthorizationInfo for the given principals.
     * 
     * @param principals the primary identifying principals of the AuthorizationInfo that should be retrieved.
     * @return the AuthorizationInfo associated with this principals.
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(final PrincipalCollection principals) {
        // create simple authorization info
        final SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        // add default roles
        simpleAuthorizationInfo.addRoles(split(this.defaultRoles));
        // add default permissions
        simpleAuthorizationInfo.addStringPermissions(split(this.defaultPermissions));
        return simpleAuthorizationInfo;
    }
    
    /**
     * Split a string into a list of not empty and trimmed strings, delimiter is a comma.
     * 
     * @param s the input string
     * @return the list of not empty and trimmed strings
     */
    protected List<String> split(final String s) {
        final List<String> list = new ArrayList<String>();
        final String[] elements = StringUtils.split(s, ',');
        if (elements != null && elements.length > 0) {
            for (final String element : elements) {
                if (StringUtils.hasText(element)) {
                    list.add(element.trim());
                }
            }
        }
        return list;
    }
    
	public OAuth20Service getOauth20Service() {
		return oauth20Service;
	}

	public void setOauth20Service(OAuth20Service oauth20Service) {
		this.oauth20Service = oauth20Service;
	}

	public void setDefaultRoles(final String defaultRoles) {
        this.defaultRoles = defaultRoles;
    }
    
    public void setDefaultPermissions(final String defaultPermissions) {
        this.defaultPermissions = defaultPermissions;
    }
}
