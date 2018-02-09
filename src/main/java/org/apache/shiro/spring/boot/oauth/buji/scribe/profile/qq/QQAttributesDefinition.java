package org.apache.shiro.spring.boot.oauth.buji.scribe.profile.qq;

import org.scribe.up.profile.OAuthAttributesDefinition;
import org.scribe.up.profile.converter.Converters;

/**
 */
public class QQAttributesDefinition extends OAuthAttributesDefinition {

    public static final String OPEN_ID = "openid";
    public static final String NICK_NAME = "nickname";
    /**
     * 用户性别,1为男性,2为女性
     */
    public static final String SEX = "sex";
    public static final String COUNTRY = "country";
    public static final String PROVINCE = "province";
    public static final String CITY = "city";
    public static final String HEAD_IMG_URL = "headimgurl";
    public static final String PRIVILEGE = "privilege";

    public QQAttributesDefinition() {
        addAttribute(OPEN_ID, Converters.stringConverter);
        addAttribute(NICK_NAME, Converters.stringConverter);
        addAttribute(SEX, Converters.integerConverter);
        addAttribute(COUNTRY, Converters.stringConverter);
        addAttribute(PROVINCE, Converters.stringConverter);
        addAttribute(CITY, Converters.stringConverter);
        addAttribute(HEAD_IMG_URL, Converters.stringConverter);
        addAttribute(PRIVILEGE, Converters.stringConverter);
    }
}