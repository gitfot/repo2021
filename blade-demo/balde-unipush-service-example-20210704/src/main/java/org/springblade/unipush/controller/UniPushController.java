package org.springblade.unipush.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springblade.core.tool.api.R;
import org.springblade.unipush.config.UniPushProperties;
import org.springblade.unipush.constant.PushConstant;
import org.springblade.unipush.dto.Notification;
import org.springblade.unipush.service.UniPushService;
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
@Api(value = "推送API", tags = "推送API")
public class UniPushController {

	private final UniPushService uniPushService;
	private final UniPushProperties uniPushProperties;

	/**
	 * 单推 cid
	 */
	@PostMapping("/cid")
	@ApiOperation(value = "推送到指定cid的客户端", notes = "cid单推")
	@ApiOperationSupport(order = 1)
	public R pushSingle(@RequestBody Notification notification) {
		Boolean flag = uniPushService.pushToSingle(notification.getAliasOrCid(),
			notification.getTitle(),
			notification.getContent(), PushConstant.PUSH_TYPE_CID);
		return R.status(flag);
	}

	/**
	 * 全推
	 */
	@PostMapping("/all")
	@ApiOperation(value = "推送到全部客户端", notes = "推送到全部客户端")
	@ApiOperationSupport(order = 2)
	public R pushAll(@RequestBody Notification notification) {
		List<String> appIds = Collections.singletonList(uniPushProperties.getAppId());
		Boolean flag = uniPushService.pushToAll(notification.getTitle(), notification.getContent(), appIds);
		return R.status(flag);
	}

	/**
	 * 单推 alias
	 */
	@PostMapping("/alias")
	@ApiOperation(value = "推送到指定alias的客户端", notes = "alias单推")
	@ApiOperationSupport(order = 3)
	public R pushByAlias(@RequestBody Notification notification) {
		Boolean flag = uniPushService.pushToSingle(notification.getAliasOrCid(),
			notification.getTitle(),
			notification.getContent(), PushConstant.PUSH_TYPE_ALIAS);
		return R.status(flag);
	}

	/**
	 * 别名绑定
	 */
	@PostMapping("/bind")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "将cid和别名绑定", notes = "将cid和别名绑定")
	public void bindAlias(@RequestParam String cid, @RequestParam String alias) {
		uniPushService.bindAlias(cid, alias);
	}

}
