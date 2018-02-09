package org.apache.shiro.spring.boot.oauth.buji.scribe.profile.weixin;

import java.util.Locale;

import org.scribe.up.profile.BaseOAuthProfile;
import org.scribe.up.profile.CommonProfile;
import org.scribe.up.profile.Gender;

/**
 * 用于添加返回用户信息
 */
public class WeiXinProfile extends BaseOAuthProfile implements CommonProfile {

    private static final long serialVersionUID = -7969484323692570444L;

	@Override
	public String getEmail() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFirstName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFamilyName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDisplayName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Gender getGender() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Locale getLocale() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPictureUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProfileUrl() {
		return null;
	}

	@Override
	public String getLocation() {
		return null;
	}

   /* protected AttributesDefinition getAttributesDefinition() {
        return new WeiXinAttributesDefinition();
    }*/

}
