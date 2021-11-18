package org.springblade.jpush.constant;

/**
 * @author zz
 * @date 2021/6/30
 */
public class JpushConstant {

	/**
	 * 推送类型 android
	 */
	public static final Integer PLATFORM_ANDROID = 1;
	/**
	 * 推送类型 ios
	 */
	public static final Integer PLATFORM_IOS = 2;
	/**
	 * 推送类型 android ios
	 */
	public static final Integer PLATFORM_ANDROID_IOS = 3;

	/**
	 * 推送类型 全平台
	 */
	public static final Integer PLATFORM_ALLPLATFORM = 3;

	/**
	 * 推送类型 根据tag标签推送
	 */
	public static final Integer AUDIENCE_TYPE_TAGS = 1;
	/**
	 * 推送类型 根据alis别名推送
	 */
	public static final Integer AUDIENCE_TYPE_ALIAS = 2;
	/**
	 * 推送类型 根据registrationId推送
	 */
	public static final Integer AUDIENCE_TYPE_REGISTRATION_ID= 3;
	/**
	 * 推送类型 广播
	 */
	public static final Integer AUDIENCE_TYPE_ALL= 4;
}
