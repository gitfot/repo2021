package org.springblade.unipush.service;

import com.gexin.rp.sdk.base.IAliasResult;
import com.gexin.rp.sdk.base.IQueryResult;
import com.gexin.rp.sdk.base.ITemplate;
import com.gexin.rp.sdk.base.impl.Message;

import java.util.List;

/**
 * @author zz
 * @date 2021/7/5
 */
public interface UniPushService {

	/**
	 * 单推
	 */
	Boolean pushToSingle(String cidOrAlias, String title, String msg, int type);

	/**
	 * 全推送
	 */
	Boolean pushToAll(String title, String msg, List<String> appIdList);

	/**
	 * 初始化消息对象
	 */
	Message getMessage(ITemplate template, Integer messageType);

	/**
	 * 绑定别名
	 */
	void bindAlias(String cid, String alias);

	/**
	 * 设置标签
	 */
	void setTag(String cid, List<String> tagList);

	/**
	 * 包装的一个判断用户cid状态离线还是在线的方法，代替了之前用type决定推送模板的方式
	 */
	String CodeStatus(String alias);

	/**
	 * 设置消息数据
	 */
	Boolean setMessageData(Message message, String title, String msg, String phoneNumberOrCid);

	/**
	 * 根据别名查询cid
	 */
	IAliasResult queryClientId(String alias);

	/**
	 * 根据cid查询用户状态
	 */
	IQueryResult getClientIdStatus(String cid);

}
