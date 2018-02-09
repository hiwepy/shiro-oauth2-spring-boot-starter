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
package org.apache.shiro.spring.boot.oauth.buji.scribe.profile.sina;

import java.util.Date;
import java.util.Locale;

import org.scribe.up.profile.AttributesDefinition;
import org.scribe.up.profile.BaseOAuthProfile;
import org.scribe.up.profile.CommonProfile;
import org.scribe.up.profile.Gender;
import org.scribe.up.profile.OAuthAttributesDefinitions;

/**
 * http://open.weibo.com/wiki/2/statuses/user_timeline
 */
public class SinaProfile extends BaseOAuthProfile implements CommonProfile {
    
    private static final long serialVersionUID = -4727966916198223807L;
    
    @Override
    protected AttributesDefinition getAttributesDefinition() {
        return OAuthAttributesDefinitions.githubDefinition;
    }
    
    public String getEmail() {
        return (String) get(SinaAttributesDefinition.EMAIL);
    }
    
    public String getFirstName() {
        return null;
    }
    
    public String getFamilyName() {
        return null;
    }
    
    public String getDisplayName() {
        return (String) get(SinaAttributesDefinition.NAME);
    }
    
    public String getUsername() {
        return (String) get(SinaAttributesDefinition.LOGIN);
    }
    
    public Gender getGender() {
        return Gender.UNSPECIFIED;
    }
    
    public Locale getLocale() {
        return null;
    }
    
    public String getPictureUrl() {
        return (String) get(SinaAttributesDefinition.AVATAR_URL);
    }
    
    public String getProfileUrl() {
        return (String) get(SinaAttributesDefinition.HTML_URL);
    }
    
    public String getLocation() {
        return (String) get(SinaAttributesDefinition.LOCATION);
    }
    
    public String getCompany() {
        return (String) get(SinaAttributesDefinition.COMPANY);
    }
    
    public Integer getFollowing() {
        return (Integer) get(SinaAttributesDefinition.FOLLOWING);
    }
    
    public String getBlog() {
        return (String) get(SinaAttributesDefinition.BLOG);
    }
    
    public Integer getPublicRepos() {
        return (Integer) get(SinaAttributesDefinition.PUBLIC_REPOS);
    }
    
    public Integer getPublicGists() {
        return (Integer) get(SinaAttributesDefinition.PUBLIC_GISTS);
    }
    
    public Integer getDiskUsage() {
        return (Integer) get(SinaAttributesDefinition.DISK_USAGE);
    }
    
    public Integer getCollaborators() {
        return (Integer) get(SinaAttributesDefinition.COLLABORATORS);
    }
    
//    public GitHubPlan getPlan() {
//        return (GitHubPlan) get(SinaAttributesDefinition.PLAN);
//    }
    
    public Integer getOwnedPrivateRepos() {
        return (Integer) get(SinaAttributesDefinition.OWNED_PRIVATE_REPOS);
    }
    
    public Integer getTotalPrivateRepos() {
        return (Integer) get(SinaAttributesDefinition.TOTAL_PRIVATE_REPOS);
    }
    
    public Integer getPrivateGists() {
        return (Integer) get(SinaAttributesDefinition.PRIVATE_GISTS);
    }
    
    public Integer getFollowers() {
        return (Integer) get(SinaAttributesDefinition.FOLLOWERS);
    }
    
    public Date getCreatedAt() {
        return (Date) get(SinaAttributesDefinition.CREATED_AT);
    }
    
    public String getType() {
        return (String) get(SinaAttributesDefinition.TYPE);
    }
    
    public String getGravatarId() {
        return (String) get(SinaAttributesDefinition.GRAVATAR_ID);
    }
    
    public String getUrl() {
        return (String) get(SinaAttributesDefinition.URL);
    }
    
    public Boolean getHireable() {
        return (Boolean) get(SinaAttributesDefinition.HIREABLE);
    }
    
    public String getBio() {
        return (String) get(SinaAttributesDefinition.BIO);
    }
}

