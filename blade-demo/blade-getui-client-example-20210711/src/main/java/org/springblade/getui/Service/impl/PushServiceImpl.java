package org.springblade.getui.Service.impl;

import com.getui.push.v2.sdk.api.PushApi;
import com.getui.push.v2.sdk.api.StatisticApi;
import com.getui.push.v2.sdk.api.UserApi;
import com.getui.push.v2.sdk.common.ApiResult;
import com.getui.push.v2.sdk.dto.req.Audience;
import com.getui.push.v2.sdk.dto.req.AudienceDTO;
import com.getui.push.v2.sdk.dto.req.Settings;
import com.getui.push.v2.sdk.dto.req.Strategy;
import com.getui.push.v2.sdk.dto.req.message.PushChannel;
import com.getui.push.v2.sdk.dto.req.message.PushDTO;
import com.getui.push.v2.sdk.dto.req.message.PushMessage;
import com.getui.push.v2.sdk.dto.req.message.android.AndroidDTO;
import com.getui.push.v2.sdk.dto.req.message.android.GTNotification;
import com.getui.push.v2.sdk.dto.req.message.android.ThirdNotification;
import com.getui.push.v2.sdk.dto.req.message.android.Ups;
import com.getui.push.v2.sdk.dto.req.message.ios.Alert;
import com.getui.push.v2.sdk.dto.req.message.ios.Aps;
import com.getui.push.v2.sdk.dto.req.message.ios.IosDTO;
import com.getui.push.v2.sdk.dto.res.TaskIdDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springblade.core.tool.utils.Func;
import org.springblade.getui.Service.PushService;
import org.springblade.getui.constant.PushConstant;
import org.springblade.getui.dto.PushParam;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author zz
 * @date 2021/7/11
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class PushServiceImpl implements PushService {

	private final PushApi pushApi;
	private final UserApi userApi;
	private final StatisticApi statisticApi;

    /**
     * 单推
	 * @param pushType 推送类型 可选cid或者别名
	 * @param isTrans 是否透传 1是 2 否
     */
	public ApiResult<?> customPush(PushParam param, Integer pushType, Integer isTrans) {
		//是否透传
		PushDTO<Audience> pushDTO = isTrans.equals(PushConstant.IS_TRANSMISSION_YES) ? new PushDTO<>() : getPushDTO(param);

		if (isTrans.equals(PushConstant.IS_TRANSMISSION_YES)) {
			PushMessage pushMessage = new PushMessage();
			pushMessage.setTransmission(" {title:\""+param.getTitle()+"\",content:\""+param.getContent()+"\",payload:\""+param.getPayload()+"\"}");
			pushDTO.setPushMessage(pushMessage);
		}

		Strategy strategy=new Strategy();
		// 1: 表示该消息在用户在线时推送个推通道，用户离线时推送厂商通道;
		// 2: 表示该消息只通过厂商通道策略下发，不考虑用户是否在线;
		// 3: 表示该消息只通过个推通道下发，不考虑用户是否在线；
		// 4: 表示该消息优先从厂商通道下发，若消息内容在厂商通道代发失败后会从个推通道下发。
		strategy.setDef(1);
		Settings settings=new Settings();
		settings.setStrategy(strategy);
		pushDTO.setRequestId(System.currentTimeMillis() + ""); //请求唯一标识号，10-32位之间
		pushDTO.setSettings(settings); //推送条件设置
		settings.setTtl(3600 * 1000 * 24); //消息有效期，走厂商消息需要设置该值

		//设置厂商通道
		PushChannel pushChannel = getPushChannel(PushConstant.CHANNEL_TYPE_ANDROID, param);
		pushDTO.setPushChannel(pushChannel);

		Audience audience = new Audience();
		ApiResult<Map<String, Map<String, String>>> apiResult = null ;
		//选择推送方式
		switch (pushType) {
			case PushConstant.PUSH_TYPE_CID:
				audience.addCid(param.getCidOrAlias());
				pushDTO.setAudience(audience);
				apiResult = pushApi.pushToSingleByCid(pushDTO);
				break;
			case PushConstant.PUSH_TYPE_ALIAS:
				audience.addAlias(param.getCidOrAlias());
				pushDTO.setAudience(audience);
				apiResult = pushApi.pushToSingleByAlias(pushDTO);
				break;
			case PushConstant.PUSH_TYPE_ALL:
				apiResult = pushApi.pushAll(getPushDTO(param));
				break;
			case PushConstant.PUSH_TYPE_CIDS:
				//cid批量推送需要先创建消息体，从而获得taskId
				ApiResult<TaskIdDTO> pushMessage = pushApi.createMsg(pushDTO);
				if (!pushMessage.isSuccess()) {
					return pushMessage;
				}
				audience.setCid(Func.toStrList(param.getCidOrAlias()));
				AudienceDTO audienceDTO = new AudienceDTO();
				audienceDTO.setAudience(audience);
				audienceDTO.setTaskid(pushMessage.getData().getTaskId());
				apiResult = pushApi.pushListByCid(audienceDTO);
		}
		return apiResult;
	}

	/**
	 * 设置厂商推送通道
	 * @param channelType 类型 可选android和iOS
	 * @return PushChannel
	 */
	public PushChannel getPushChannel(String channelType, PushParam param) {
		PushChannel pushChannel = new PushChannel();
		if (channelType.equals("ios")) {
			Alert alert=new Alert();
			alert.setTitle(param.getTitle());
			alert.setBody(param.getContent());
			Aps aps = new Aps();
			//1表示静默推送(无通知栏消息)，静默推送时不需要填写其他参数。
			//苹果建议1小时最多推送3条静默消息
			aps.setContentAvailable(0);
			aps.setSound("default");
			aps.setAlert(alert);
			IosDTO iosDTO = new IosDTO();
			iosDTO.setAps(aps);
			iosDTO.setType("notify");
			pushChannel.setIos(iosDTO);
		}
		else {
			AndroidDTO androidDTO = new AndroidDTO();
			Ups ups = new Ups();
			ThirdNotification notification1 = new ThirdNotification();;
			ups.setNotification(notification1);
			notification1.setTitle(param.getTitle());
			notification1.setBody(param.getContent());
			notification1.setPayload(String.valueOf(param.getPayload()));
			notification1.setClickType("intent");
			//intent内容固定
			notification1.setIntent("intent:#Intent;launchFlags=0x04000000;action=android.intent.action.oppopush;component=io.dcloud.HBuilder/io.dcloud.PandoraEntry;S.UP-OL-SU=true;S.title="+param.getTitle()+";S.content="+param.getContent()+";S.payload="+param.getPayload()+";end");
			androidDTO.setUps(ups);
			pushChannel.setAndroid(androidDTO);
		}
		return pushChannel;
	}

	/**
	 * 获取个推通道推送对象（非透传）
	 * @return PushDTO
	 */
	public PushDTO getPushDTO(PushParam param) {
		PushDTO<?> pushDTO = new PushDTO<>();

		PushMessage pushMessage = new PushMessage();
		// 1.1 设置推送内容
		GTNotification notification = new GTNotification();
		notification.setTitle(param.getTitle());
		notification.setBody(param.getContent());
		notification.setUrl(param.getPayload().get("url"));
		notification.setClickType("url");
		pushMessage.setNotification(notification);
		pushDTO.setPushMessage(pushMessage);
		return pushDTO;
	}
}
