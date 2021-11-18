package org.springblade.getui.constant;

/**
 * @author zz
 * @date 2021/7/5
 */
public class PushConstant {

	/**
	 * 推送方式-别名推送
	 */
	public static final int PUSH_TYPE_ALIAS = 1;
	/**
	 * 推送方式-cid推送
	 */
	public static final int PUSH_TYPE_CID = 2;
	/**
	 * 推送方式-全推送
	 */
	public static final int PUSH_TYPE_ALL = 3;
	/**
	 * 推送方式-cid批量推送
	 */
	public static final int PUSH_TYPE_CIDS = 4;

	/**
	 * 消息类型-单推
	 */
	public static final int MESSAGE_TYPE_SINGLE = 1;
	/**
	 * 消息类型-群推
	 */
	public static final int MESSAGE_TYPE_ALL = 2;

	/**
	 * 厂商通道 iOS
	 */
	public static final String CHANNEL_TYPE_IOS = "iOS";
	/**
	 * 厂商通道 安卓
	 */
	public static final String CHANNEL_TYPE_ANDROID = "android";

	/**
	 * 是否为透传 是
	 */
	public static final Integer IS_TRANSMISSION_YES = 1;
	/**
	 * 是否为透传 否
	 */
	public static final Integer IS_TRANSMISSION_NO = 2;
}
