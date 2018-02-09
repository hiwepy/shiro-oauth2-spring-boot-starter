package org.apache.shiro.spring.boot.oauth.buji.scribe.profile.qq;

import java.util.Locale;

import org.scribe.up.profile.BaseOAuthProfile;
import org.scribe.up.profile.CommonProfile;
import org.scribe.up.profile.Gender;

/**
 */
public class QQProfile extends BaseOAuthProfile implements CommonProfile {

    /*@Override
    protected AttributesDefinition getAttributesDefinition() {
        return new QQAttributesDefinition();
    }*/

	public static final String CLIENTID = "client_id";
	public static final String ID = "id";

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLocation() {
		// TODO Auto-generated method stub
		return null;
	}
}
