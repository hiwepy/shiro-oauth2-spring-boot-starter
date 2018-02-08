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
 * This class is the user profile for GitHub with appropriate getters.<br />
 * It is returned by the {@link org.scribe.up.provider.impl.GitHubProvider}.
 * <p />
 * <table border="1" cellspacing="2px">
 * <tr>
 * <th>Method :</th>
 * <th>From the JSON profile response :</th>
 * </tr>
 * <tr>
 * <th colspan="2">The attributes of the {@link org.scribe.up.profile.CommonProfile}</th>
 * </tr>
 * <tr>
 * <td>String getEmail()</td>
 * <td>the <i>email</i> attribute</td>
 * </tr>
 * <tr>
 * <td>String getFirstName()</td>
 * <td>null</td>
 * </tr>
 * <tr>
 * <td>String getFamilyName()</td>
 * <td>null</td>
 * </tr>
 * <tr>
 * <td>String getDisplayName()</td>
 * <td>the <i>name</i> attribute</td>
 * </tr>
 * <tr>
 * <td>String getUsername()</td>
 * <td>the <i>login</i> attribute</td>
 * </tr>
 * <tr>
 * <td>Gender getGender()</td>
 * <td>{@link org.scribe.up.profile.Gender#UNSPECIFIED}</td>
 * </tr>
 * <tr>
 * <td>Locale getLocale()</td>
 * <td>null</td>
 * </tr>
 * <tr>
 * <td>String getPictureUrl()</td>
 * <td>the <i>avatar_url</i> attribute</td>
 * </tr>
 * <tr>
 * <td>String getProfileUrl()</td>
 * <td>the <i>html_url</i> attribute</td>
 * </tr>
 * <tr>
 * <td>String getLocation()</td>
 * <td>the <i>location</i> attribute</td>
 * </tr>
 * <tr>
 * <th colspan="2">More specific attributes</th>
 * </tr>
 * <tr>
 * <td>String getCompany()</td>
 * <td>the <i>company</i> attribute</td>
 * </tr>
 * <tr>
 * <td>Integer getFollowing()</td>
 * <td>the <i>following</i> attribute</td>
 * </tr>
 * <tr>
 * <td>String getBlog()</td>
 * <td>the <i>blog</i> attribute</td>
 * </tr>
 * <tr>
 * <td>Integer getPublicRepos()</td>
 * <td>the <i>public_repos</i> attribute</td>
 * </tr>
 * <tr>
 * <td>Integer getPublicGists()</td>
 * <td>the <i>public_gists</i> attribute</td>
 * </tr>
 * <tr>
 * <td>Integer getDiskUsage()</td>
 * <td>the <i>disk_usage</i> attribute</td>
 * </tr>
 * <tr>
 * <td>Integer getCollaborators()</td>
 * <td>the <i>collaborators</i> attribute</td>
 * </tr>
 * <tr>
 * <td>GitHubPlan getPlan()</td>
 * <td>the <i>plan</i> attribute</td>
 * </tr>
 * <tr>
 * <td>Integer getOwnedPrivateRepos()</td>
 * <td>the <i>owned_private_repos</i> attribute</td>
 * </tr>
 * <tr>
 * <td>Integer getTotalPrivateRepos()</td>
 * <td>the <i>total_private_repos</i> attribute</td>
 * </tr>
 * <tr>
 * <td>Integer getPrivateGists()</td>
 * <td>the <i>private_gists</i> attribute</td>
 * </tr>
 * <tr>
 * <td>Integer getFollowers()</td>
 * <td>the <i>followers</i> attribute</td>
 * </tr>
 * <tr>
 * <td>Date getCreatedAt()</td>
 * <td>the <i>created_at</i> attribute</td>
 * </tr>
 * <tr>
 * <td>String getType()</td>
 * <td>the <i>type</i> attribute</td>
 * </tr>
 * <tr>
 * <td>String getGravatarId()</td>
 * <td>the <i>gravatar_id</i> attribute</td>
 * </tr>
 * <tr>
 * <td>String getUrl()</td>
 * <td>the <i>url</i> attribute</td>
 * </tr>
 * <tr>
 * <td>Boolean getHireable()</td>
 * <td>the <i>hireable</i> attribute</td>
 * </tr>
 * <tr>
 * <td>String getBio()</td>
 * <td>the <i>bio</i> attribute</td>
 * </tr>
 * </table>
 * 
 * @see org.apache.shiro.spring.boot.oauth.buji.scribe.provider.SinaWeiboProvider
 * @author Jerome Leleu
 * @since 1.1.0
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

