package org.springblade.jpush.util;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.ServiceHelper;
import cn.jiguang.common.connection.NettyHttpClient;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.ResponseWrapper;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.Notification;
import io.netty.handler.codec.http.HttpMethod;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springblade.jpush.config.JpushProperties;
import org.springblade.jpush.constant.JpushConstant;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * 消息推送工具类
 * @author zz
 * @date 2021/6/29
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JpushUtils {

	private final JpushProperties jpushProperties;

	public JPushClient getJPushClient() {
		//初始化客户端
		ClientConfig clientConfig = ClientConfig.getInstance();
		clientConfig.setTimeToLive(Long.parseLong(jpushProperties.getLiveTime()));
		// 使用NativeHttpClient网络客户端，连接网络的方式，不提供回调函数
		return new JPushClient(jpushProperties.getMasterSecret(), jpushProperties.getAppKey(), null, clientConfig);
	}

	/**
	 * 全类型推送
	 *
	 * @param title     App通知栏标题
	 * @param content   App通知栏内容（为了单行显示全，尽量保持在22个汉字以下）
	 * @param extrasMap 额外推送信息（不会显示在通知栏，传递数据用）
	 */
	public void pushAll(String title, String content, Map<String, String> extrasMap) {
		PushPayload payload = customBuildPushLoad(title, content, extrasMap, JpushConstant.PLATFORM_ANDROID_IOS, JpushConstant.AUDIENCE_TYPE_ALL, "");
		log.info("Got result:" + payload);
		sendPushWithLog(payload);
	}

	/**
	 * 根据alias发送，由APP端拦截信息后再决定是否创建通知(目前APP用此种方式)
	 */
	public void pushByAlias(String title, String content, Map<String, String> extrasMap, String... alias) {
		// 设置为消息推送方式为仅推送消息，不创建通知栏提醒
		PushPayload payload = customBuildPushLoad(title, content, extrasMap, JpushConstant.PLATFORM_ANDROID_IOS, JpushConstant.AUDIENCE_TYPE_ALIAS,alias);
		log.info("Got result:" + payload);
		sendPushWithLog(payload);
	}

	/**
	 * 根据tags发送通知消息
	 */
	public void pushByTags(String title, String content, Map<String, String> extrasMap, String... tags) {
		PushPayload payload = customBuildPushLoad(title, content, extrasMap, JpushConstant.PLATFORM_ANDROID_IOS, JpushConstant.AUDIENCE_TYPE_TAGS, tags);
		log.info("Got result:" + payload);
		sendPushWithLog(payload);
	}

	/**
	 * 根据registrationId 发送通知
	 */
	public void pushByRID(String title, String content, Map<String, String> extrasMap, String... targets) {
		PushPayload payload = customBuildPushLoad(title, content, extrasMap, JpushConstant.PLATFORM_ANDROID_IOS, JpushConstant.AUDIENCE_TYPE_REGISTRATION_ID, targets);
		log.info("Got result:" + payload);
		sendPushWithLog(payload);
	}

	/**
	 * 异步请求推送方式
	 * 使用NettyHttpClient,异步接口发送请求，通过回调函数可以获取推送成功与否情况
	 *
	 * @param title     通知栏标题
	 * @param content   通知栏内容（为了单行显示全，尽量保持在22个汉字以下）
	 * @param extrasMap 额外推送信息（不会显示在通知栏，传递数据用）
	 * @param alias     需接收的用户别名数组（为空则所有用户都推送）
	 */
	public void pushWithCallback(String title, String content, Map<String, String> extrasMap, String... alias) {
		ClientConfig clientConfig = ClientConfig.getInstance();
		clientConfig.setTimeToLive(Long.parseLong(jpushProperties.getLiveTime()));
		String host = (String) clientConfig.get(ClientConfig.PUSH_HOST_NAME);
		NettyHttpClient client = new NettyHttpClient(
			ServiceHelper.getBasicAuthorization(jpushProperties.getAppKey(), jpushProperties.getMasterSecret()), null,
			clientConfig);
		try {
			URI uri = new URI(host + clientConfig.get(ClientConfig.PUSH_PATH));
			PushPayload payload = customBuildPushLoad(title, content, extrasMap, JpushConstant.PLATFORM_ANDROID_IOS, JpushConstant.AUDIENCE_TYPE_ALIAS, alias);
			log.info("Got result:" + payload);
			client.sendRequest(HttpMethod.POST, payload.toString(), uri, new NettyHttpClient.BaseCallback() {
				@Override
				public void onSucceed(ResponseWrapper responseWrapper) {
					if (200 == responseWrapper.responseCode) {
						log.info("极光推送成功");
					} else {
						log.info("极光推送失败，返回结果: " + responseWrapper.responseContent);
					}
				}
			});
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} finally {
			// 需要手动关闭Netty请求进程,否则会一直保留
			client.close();
		}
	}

	/**
	 * 根据registrationId设置客户端的别名、标签信息
	 *
	 * @param registrationId 设备的registrationId
	 * @param alias          更新设备的别名属性
	 * @param tagsToAdd      添加设备的tag属性
	 * @param tagsToRemove   移除设备的tag属性
	 */
	public void updateDeviceTagAlias(String registrationId, String alias, Set<String> tagsToAdd, Set<String> tagsToRemove) {
		JPushClient jpushClient = new JPushClient(jpushProperties.getMasterSecret(), jpushProperties.getAppKey());

		try {
			jpushClient.updateDeviceTagAlias(registrationId, alias, tagsToAdd, tagsToRemove);
		}
		catch (APIConnectionException e) {
			log.error("Connection error. Should retry later. ", e);
		}
		catch (APIRequestException e) {
			log.error("Error response from JPush server. Should review and fix it. ", e);
			log.info("HTTP Status: " + e.getStatus());
			log.info("Error Code: " + e.getErrorCode());
			log.info("Error Message: " + e.getErrorMessage());
			log.info("Msg ID: " + e.getMsgId());
		}

	}

	/**
	 * 自定义构建推送载体
	 * @param title 标题
	 * @param content 内容
	 * @param extrasMap 附件内容
	 * @param platform 平台 --为空时为安卓和ios
	 * @param audienceType 目标类型 --为空时全目标
	 * @param target 推送目标
	 * @return PushPayload
	 */
	public PushPayload customBuildPushLoad(String title,String content, Map<String,String> extrasMap, Integer platform, Integer audienceType, String... target) {
		if (extrasMap == null || extrasMap.isEmpty()) {
			extrasMap = new HashMap<>();
		}
		PushPayload.Builder builder = PushPayload.newBuilder();

		//设置推送平台平台
		if (platform.equals(JpushConstant.PLATFORM_ANDROID)) {
			builder.setPlatform(Platform.android());
		}
		else if (platform.equals(JpushConstant.PLATFORM_IOS)) {
			builder.setPlatform(Platform.ios());
		}
		else if (platform.equals(JpushConstant.PLATFORM_ANDROID_IOS)) {
			builder.setPlatform(Platform.android_ios());
		} else {
			builder.setPlatform(Platform.all());
		}

		//批量删除数组中的空元素
		String[] audiences = removeArrayEmptyElement(target);

		//设置推送目标类型
		if (audienceType.equals(JpushConstant.AUDIENCE_TYPE_TAGS)) {
			builder.setAudience(Audience.tag(audiences));
		}
		else if (audienceType.equals(JpushConstant.AUDIENCE_TYPE_ALIAS)) {
			builder.setAudience(Audience.alias(audiences));
		}
		else if (audienceType.equals(JpushConstant.AUDIENCE_TYPE_REGISTRATION_ID)) {
			builder.setAudience(Audience.registrationId(audiences));
		} else {
			builder.setAudience(Audience.all());
		}

		//设置通知方式
		builder.setNotification(Notification.newBuilder().setAlert(content)
			.addPlatformNotification(AndroidNotification.newBuilder().setTitle(title).addExtras(extrasMap).build()).build());

		return builder.build();
	}

	/**
	 * 统一推送接口
	 * @param payload PushPayload 对象
	 */
	public void sendPushWithLog(PushPayload payload) {
		try {
			getJPushClient().sendPush(payload);
		}
		catch (APIConnectionException e) {
			log.error("Connection error. Should retry later. ", e);
			log.error("Sendno: " + payload.getSendno());
		}
		catch (APIRequestException e) {
			log.error("Error response from JPush server. Should review and fix it. ", e);
			log.info("HTTP Status: " + e.getStatus());
			log.info("Error Code: " + e.getErrorCode());
			log.info("Error Message: " + e.getErrorMessage());
			log.info("Msg ID: " + e.getMsgId());
			log.error("Sendno: " + payload.getSendno());
		}
	}

	/**
	 * 删除别名中的空元素（需删除如：null,""," "）
	 */
	private String[] removeArrayEmptyElement(String... strArray) {
		if (null == strArray || strArray.length == 0) {
			return null;
		}
		List<String> tempList = Arrays.asList(strArray);
		List<String> strList = new ArrayList<String>();
		for (String str : tempList) {
			// 消除空格后再做比较
			if (null != str && !"".equals(str.trim())) {
				strList.add(str);
			}
		}
		// 若仅输入"",则会将数组长度置为0
		String[] newStrArray = strList.toArray(new String[strList.size()]);
		return newStrArray;
	}
}
