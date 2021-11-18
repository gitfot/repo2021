package org.springblade.unipush.controller;

import lombok.RequiredArgsConstructor;
import org.springblade.core.tool.api.R;
import org.springblade.unipush.config.UniPushProperties;
import org.springblade.unipush.constant.PushConstant;
import org.springblade.unipush.dto.Notification;
import org.springblade.unipush.service.impl.UniPushServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * @author zz
 * @date 2021/7/5
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/push")
public class PushController {

	private final UniPushServiceImpl uniPushService;
	private final UniPushProperties uniPushProperties;

	/**
	 * 单推 cid
	 */
	@PostMapping("/cid")
	public R pushSingle(@RequestBody Notification notification) {
		//Boolean flag = uniPushService.pushToSingle("71d99e6f2e8600358cac7fbf2c8973fd", "test", "高山仰止，景行行止。——佚名《车舝》", PushConstant.PUSH_TYPE_CID);
		Boolean flag = uniPushService.pushToSingle(notification.getAliasOrCid(),
			notification.getTitle(),
			notification.getContent(), PushConstant.PUSH_TYPE_CID);
		return R.status(flag);
	}

	/**
	 * 全推
	 */
	@PostMapping("/all")
	public R pushAll(@RequestBody Notification notification) {
		List<String> appIds = Collections.singletonList(uniPushProperties.getAppId());
//		Boolean flag = uniPushService.pushToAll("test", "春风得意马蹄疾，一日看尽长安花。——孟郊《登科后》", appIds);
		Boolean flag = uniPushService.pushToAll(notification.getTitle(), notification.getContent(), appIds);
		return R.status(flag);
	}

	/**
	 * 单推 alias
	 */
	@PostMapping("/alias")
	public R pushByAlias(@RequestBody Notification notification) {
//		Boolean flag = uniPushService.pushToSingle("test", "每日一句", "大鹏一日同风起，扶摇直上九万里。——李白《上李邕》", PushConstant.PUSH_TYPE_ALIAS);
		Boolean flag = uniPushService.pushToSingle(notification.getAliasOrCid(),
			notification.getTitle(),
			notification.getContent(), PushConstant.PUSH_TYPE_ALIAS);
		return R.status(flag);
	}

	@PostMapping("/bind")
	public void bindAlias(@RequestParam String cid, @RequestParam String alias) {
		uniPushService.bindAlias(cid, alias);
	}

	@PostMapping("/bindTest")
	public void bindAlias() {
		uniPushService.bindAlias("71d99e6f2e8600358cac7fbf2c8973fd", "test");
	}
}
