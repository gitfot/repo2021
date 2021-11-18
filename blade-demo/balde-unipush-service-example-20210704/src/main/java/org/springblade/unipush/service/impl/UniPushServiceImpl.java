package org.springblade.unipush.service.impl;

import com.gexin.rp.sdk.base.IAliasResult;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.IQueryResult;
import com.gexin.rp.sdk.base.ITemplate;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.Message;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springblade.unipush.config.UniPushProperties;
import org.springblade.unipush.constant.PushConstant;
import org.springblade.unipush.service.UniPushService;
import org.springblade.unipush.template.PushTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 消息推送服务
 * @author zz
 * @date 2021/7/5
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class UniPushServiceImpl implements UniPushService {

	private final UniPushProperties uniPushProperties;

	private final PushTemplate pushTemplate;

	/**
	 * 单推
	 * @param cidOrAlias 别名或者cid
	 * @param msg 透传消息内容
	 * @param type 1-cid推，2-别名推
	 */
	public Boolean pushToSingle(String cidOrAlias, String title, String msg, int type) {
		IGtPush push = new IGtPush(uniPushProperties.getHost(), uniPushProperties.getAppKey(), uniPushProperties.getMasterSecret());
//		ITemplate template = pushTemplate.getTransmissionTemplate();
		ITemplate template = pushTemplate.getNotificationTemplate(title, msg);
		SingleMessage message = (SingleMessage) getMessage(template, PushConstant.MESSAGE_TYPE_SINGLE);

		Target target = new Target();
		target.setAppId(uniPushProperties.getAppId());
		if (type == PushConstant.PUSH_TYPE_CID) {
			target.setClientId(cidOrAlias);
		} else if (type == PushConstant.PUSH_TYPE_ALIAS) {
			// 按别名推送(需提前绑定别名)
			target.setAlias(cidOrAlias);
		}
		IPushResult ret;
		try {
			ret = push.pushMessageToSingle(message, target);
		} catch (RequestException e) {
			e.printStackTrace();
			// 推送失败时，进行重推
			ret = push.pushMessageToSingle(message, target, e.getRequestId());
		}
		if (ret != null) {
			log.info(ret.getResponse().toString());
			return true;
		} else {
			log.error("服务器响应异常");
			return false;
		}
	}

	/**
	 * 全推送
	 */
	public Boolean pushToAll(String title, String msg, List<String> appIdList) {
		IGtPush push = new IGtPush(uniPushProperties.getHost(), uniPushProperties.getAppKey(), uniPushProperties.getMasterSecret());
		ITemplate template = pushTemplate.getNotificationTemplate(title, msg);
		AppMessage message = (AppMessage) getMessage(template, PushConstant.MESSAGE_TYPE_ALL);
		message.setAppIdList(appIdList);
		IPushResult ret = null;
		try {
			ret = push.pushMessageToApp(message);
		} catch (RequestException e) {
			e.printStackTrace();
		}
		if (ret != null) {
			log.info(ret.getResponse().toString());
			return true;
		} else {
			log.error("服务器响应异常");
			return false;
		}
	}

	/**
	 * 初始化消息对象
	 * @param template 模板对象
	 * @return SingleMessage
	 */
	public Message getMessage(ITemplate template, Integer messageType) {
		Message message;
		if (messageType.equals(PushConstant.MESSAGE_TYPE_SINGLE)) {
			 message = new SingleMessage();
		} else {
			 message = new AppMessage();
		}
		message.setData(template);
		// 设置消息离线，并设置离线时间
		message.setOffline(true);
		// 离线有效时间，单位为毫秒，可选
		message.setOfflineExpireTime(72 * 3600 * 1000);
		// 判断客户端是否wifi环境下推送。1为仅在wifi环境下推送，0为不限制网络环境，默认不限
		message.setPushNetWorkType(0);
		// 厂商下发策略；1: 个推通道优先，在线经个推通道下发，离线经厂商下发(默认);2: 在离线只经厂商下发;3: 在离线只经个推通道下发;4: 优先经厂商下发，失败后经个推通道下发;
		message.setStrategyJson("{\"default\":4,\"ios\":4,\"st\":4}");
		return message;
	}

	/**
	 * 绑定别名
	 * @param cid clientId
	 * @param alias 别名
	 */
	public void bindAlias(String cid, String alias) {
		IGtPush push = new IGtPush(uniPushProperties.getHost(), uniPushProperties.getAppKey(), uniPushProperties.getMasterSecret());
		IAliasResult bindSCid = push.bindAlias(uniPushProperties.getAppId(), alias, cid);
		log.info("绑定结果：" + bindSCid.getResponse());
	}

	/**
	 * 设置标签
	 * @param cid cid clientId
	 * @param tagList 标签列表
	 */
	public void setTag(String cid, List<String> tagList) {
		IGtPush push = new IGtPush(uniPushProperties.getHost(), uniPushProperties.getAppKey(), uniPushProperties.getMasterSecret());
		IQueryResult ret = push.setClientTag(uniPushProperties.getAppId(), cid, tagList);
		log.info(ret.getResponse().toString());
	}

	/**
	 * 包装的一个判断用户cid状态离线还是在线的方法，代替了之前用type决定推送模板的方式
	 */
	public String CodeStatus(String alias){
		IAliasResult iAliasResult = queryClientId(alias);
		if(!iAliasResult.getResponse().get("result").equals("ok")){
			return "null";
		}
		List<String> clientIdList = iAliasResult.getClientIdList();
		if (clientIdList.size()==0){
			return "null";
		}
		String cid = clientIdList.get(0);
		IQueryResult clientIdStatus = getClientIdStatus(cid);
		Object result = clientIdStatus.getResponse().get("result");
		return result.toString();
	}

	/**
	 * 设置消息数据
	 * @param message message
	 * @param msg 消息
	 * @param phoneNumberOrCid 手机号或者cid
	 */
	public Boolean setMessageData(Message message, String title, String msg, String phoneNumberOrCid) {
		String result = CodeStatus(phoneNumberOrCid);
		if(result.equals("Offline")){
			TransmissionTemplate template = pushTemplate.getTransmissionTemplate();//离线模板1
			message.setData(template);
			return true;
		}
		if(result.equals("Online")){
			NotificationTemplate template =  pushTemplate.getNotificationTemplate(title, msg);   //通知模板 ，再多来两个模板我就要整switch了
			message.setData(template);
			return true;
		}
		else return false;
	}

	/**
	 * 根据别名查询cid
	 */
	public IAliasResult queryClientId(String alias) {
		IGtPush push = new IGtPush(uniPushProperties.getHost(), uniPushProperties.getAppKey(), uniPushProperties.getMasterSecret());
		return push.queryClientId(uniPushProperties.getAppId(), alias);
	}

	/**
	 * 根据cid查询用户状态
	 */
	public IQueryResult getClientIdStatus(String cid) {
		IGtPush push = new IGtPush(uniPushProperties.getHost(), uniPushProperties.getAppKey(), uniPushProperties.getMasterSecret());
		return  push.getClientIdStatus(uniPushProperties.getAppId(), cid);
	}
}
