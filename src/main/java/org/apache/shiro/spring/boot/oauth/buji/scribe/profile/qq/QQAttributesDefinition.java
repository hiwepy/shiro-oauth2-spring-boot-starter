package org.apache.shiro.spring.boot.oauth.buji.scribe.profile.qq;

import org.scribe.up.profile.OAuthAttributesDefinition;
import org.scribe.up.profile.converter.Converters;

/**
 * http://wiki.connect.qq.com/get_user_info
 */
public class QQAttributesDefinition extends OAuthAttributesDefinition {

	public static final String OPEN_ID = "openid";
	// 用户在QQ空间的昵称。
	public static final String NICK_NAME = "nickname";
	// 大小为30×30像素的QQ空间头像URL。
	public static final String FIGUREURL = "figureurl";
	// 大小为50×50像素的QQ空间头像URL。
	public static final String FIGUREURL_1 = "figureurl_1";
	// 大小为100×100像素的QQ空间头像URL。
	public static final String FIGUREURL_2 = "figureurl_2";
	// 大小为40×40像素的QQ头像URL。
	public static final String FIGUREURL_QQ_1 = "figureurl_qq_1";
	// 大小为100×100像素的QQ头像URL。需要注意，不是所有的用户都拥有QQ的100x100的头像，但40x40像素则是一定会有。
	public static final String FIGUREURL_QQ_2 = "figureurl_qq_2";
	// 性别。 如果获取不到则默认返回"男"
	public static final String GENDER = "gender";
	// 标识用户是否为黄钻用户（0：不是；1：是）
	public static final String VIP = "vip";
	// 标识用户是否为黄钻用户（0：不是；1：是）。
	public static final String IS_YELLOW_VIP = "is_yellow_vip";
	// 黄钻等级
	public static final String YELLOW_VIP_LEVEL = "yellow_vip_level";
	// QQ等级
	public static final String LEVEL = "level";
	// 标识是否为年费黄钻用户（0：不是； 1：是）
	public static final String IS_YELLOW_YEAR_VIP = "is_yellow_year_vip";

	public QQAttributesDefinition() {
		addAttribute(OPEN_ID, Converters.stringConverter);
		addAttribute(NICK_NAME, Converters.stringConverter);
		addAttribute(FIGUREURL, Converters.stringConverter);
		addAttribute(FIGUREURL_1, Converters.stringConverter);
		addAttribute(FIGUREURL_2, Converters.stringConverter);
		addAttribute(FIGUREURL_QQ_1, Converters.stringConverter);
		addAttribute(FIGUREURL_QQ_2, Converters.stringConverter);
		addAttribute(GENDER, Converters.stringConverter);
		addAttribute(VIP, Converters.integerConverter);
		addAttribute(IS_YELLOW_VIP, Converters.integerConverter);
		addAttribute(YELLOW_VIP_LEVEL, Converters.integerConverter);
		addAttribute(LEVEL, Converters.integerConverter);
		addAttribute(IS_YELLOW_YEAR_VIP, Converters.integerConverter);
	}
}