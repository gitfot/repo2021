package org.springblade.unipush.template;

import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.base.payload.MultiMedia;
import com.gexin.rp.sdk.template.*;
import lombok.RequiredArgsConstructor;
import org.springblade.unipush.config.UniPushProperties;
import org.springblade.unipush.style.PushStyle;
import org.springframework.stereotype.Component;

/**
 * 推送模板类
 * @author zz
 * @date 2021/7/5
 */
@Component
@RequiredArgsConstructor
public class PushTemplate {

	private final UniPushProperties uniPushProperties;

	/**
	 * 点击通知打开应用模板, 在通知栏显示一条含图标、标题等的通知，用户点击后激活您的应用。
	 * 通知模板(点击后续行为: 支持打开应用、发送透传内容、打开应用同时接收到透传 这三种行为)
	 */
	public NotificationTemplate getNotificationTemplate(String title, String msg) {
		NotificationTemplate template = new NotificationTemplate();
		template.setAppId(uniPushProperties.getAppId());
		template.setAppkey(uniPushProperties.getAppKey());
		//设置展示样式
		template.setStyle(PushStyle.getStyle0(title, msg));
		template.setTransmissionType(1);  // 透传消息设置，收到消息是否立即启动应用： 1为立即启动，2则广播等待客户端自启动
		template.setTransmissionContent("请输入您要透传的内容");
//        template.setDuration("2019-07-09 11:40:00", "2019-07-09 12:24:00");  // 设置定时展示时间，安卓机型可用
		template.setNotifyid(123); // 在消息推送的时候设置notifyId。如果需要覆盖此条消息，则下次使用相同的notifyId发一条新的消息。客户端sdk会根据notifyId进行覆盖。
		//template.setAPNInfo(getAPNPayload()); //ios消息推送
		return template;
	}

	/**
	 * 点击通知打开(第三方)网页模板, 在通知栏显示一条含图标、标题等的通知，用户点击可打开您指定的网页。
	 */
	public LinkTemplate getLinkTemplate(String title, String msg) {
		LinkTemplate template = new LinkTemplate();
		template.setAppId(uniPushProperties.getAppId());
		template.setAppkey(uniPushProperties.getAppKey());

		//设置展示样式
		template.setStyle(PushStyle.getStyle0(title, msg));
		template.setUrl("http://www.baidu.com");  // 设置打开的网址地址
		template.setNotifyid(123);
//        template.setDuration("2019-07-09 11:40:00", "2019-07-09 12:24:00");  // 设置定时展示时间，安卓机型可用
		return template;
	}

	/**
	 * 透传消息模版,透传消息是指消息传递到客户端只有消息内容，展现形式由客户端自行定义。客户端可自定义通知的展现形式，也可自定义通知到达之后的动作，或者不做任何展现。
	 */
	public TransmissionTemplate getTransmissionTemplate() {
		TransmissionTemplate template = new TransmissionTemplate();
		template.setAppId(uniPushProperties.getAppId());
		template.setAppkey(uniPushProperties.getAppKey());

		//透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
		template.setTransmissionType(1);
		template.setTransmissionContent("透传内容"); //透传内容
		template.setAPNInfo(getAPNPayload()); //ios消息推送
		return template;
	}

	//点击通知, 打开（自身）应用内任意页面
	public StartActivityTemplate getStartActivityTemplate(String title, String msg) {
		StartActivityTemplate template = new StartActivityTemplate();
		template.setAppId(uniPushProperties.getAppId());
		template.setAppkey(uniPushProperties.getAppKey());
		//设置展示样式
		template.setStyle(PushStyle.getStyle0(title, msg));

		String intent = "intent:#Intent;component=com.yourpackage/.NewsActivity;end";
		template.setIntent(intent); //最大长度限制为1000
		template.setNotifyid(123);
//        template.setDuration("2019-07-09 11:40:00", "2019-07-09 12:24:00");  // 设置定时展示时间，安卓机型可用
		return template;
	}

	/**
	 * 消息撤回模版
	 * @param taskId 任务ID
	 */
	public RevokeTemplate getRevokeTemplate(String taskId) {
		RevokeTemplate template = new RevokeTemplate();
		template.setAppId(uniPushProperties.getAppId());
		template.setAppkey(uniPushProperties.getAppKey());
		template.setOldTaskId(taskId); //指定需要撤回消息对应的taskId
		template.setForce(false); // 客户端没有找到对应的taskId，是否把对应appId下所有的通知都撤回
		return template;
	}

	/**
	 * 创建ios离线通知
	 * @return APNPayload
	 */
	private static APNPayload getAPNPayload() {
		APNPayload payload = new APNPayload();
		//在已有数字基础上加1显示，设置为-1时，在已有数字上减1显示，设置为数字时，显示指定数字
		payload.setAutoBadge("+1");
		payload.setContentAvailable(1);
		//ios 12.0 以上可以使用 Dictionary 类型的 sound
		payload.setSound("default");
		payload.setCategory("$由客户端定义");
		payload.addCustomMsg("由客户自定义消息key", "由客户自定义消息value");
		//简单模式APNPayload.SimpleMsg
		// payload.setAlertMsg(new APNPayload.SimpleAlertMsg("hello"));
		// payload.setAlertMsg(getDictionaryAlertMsg());  //字典模式使用APNPayload.DictionaryAlertMsg

		//设置语音播报类型，int类型，0.不可用 1.播放body 2.播放自定义文本
		payload.setVoicePlayType(2);
		//设置语音播报内容，String类型，非必须参数，用户自定义播放内容，仅在voicePlayMessage=2时生效
		//注：当"定义类型"=2, "定义内容"为空时则忽略不播放
		payload.setVoicePlayMessage("定义内容");

		// 添加多媒体资源
		payload.addMultiMedia(new MultiMedia()
			.setResType(MultiMedia.MediaType.pic)
			.setResUrl("资源文件地址")
			.setOnlyWifi(true));

		return payload;
	}

}
