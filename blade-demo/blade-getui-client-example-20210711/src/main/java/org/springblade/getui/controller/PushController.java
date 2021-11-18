package org.springblade.getui.controller;

import com.getui.push.v2.sdk.api.PushApi;
import com.getui.push.v2.sdk.api.StatisticApi;
import com.getui.push.v2.sdk.api.UserApi;
import com.getui.push.v2.sdk.common.ApiResult;
import com.getui.push.v2.sdk.dto.req.Audience;
import com.getui.push.v2.sdk.dto.req.CidAliasListDTO;
import com.getui.push.v2.sdk.dto.req.message.PushDTO;
import com.getui.push.v2.sdk.dto.res.TaskIdDTO;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springblade.core.tool.api.R;
import org.springblade.getui.Service.PushService;
import org.springblade.getui.constant.PushConstant;
import org.springblade.getui.dto.PushParam;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author zz
 * @date 2021/7/5
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/push")
@Slf4j
@Api(value = "推送API", tags = "推送API")
public class PushController {

	private final PushApi pushApi;
	private final UserApi userApi;
	private final StatisticApi statisticApi;
	private final PushService pushService;


	/**
	 * 在线单推
	 */
	@PostMapping("/online/single")
	@ApiOperation(value = "在线单推", notes = "推送到指定cid的客户端")
	@ApiOperationSupport(order = 1)
	public R<?> pushSimple(@RequestBody PushParam param) {
		PushDTO<Audience> pushDTO = pushService.getPushDTO(param);
		// 设置推送目标
		Audience audience = new Audience();
		audience.addCid(param.getCidOrAlias());
//		audience.addAlias("wan");
		pushDTO.setAudience(audience);

		// 进行cid单推
		ApiResult<Map<String, Map<String, String>>> apiResult = pushApi.pushToSingleByCid(pushDTO);

		log.info(String.valueOf(apiResult.getData()));
		return R.data("code:" + apiResult.getCode() + ", msg: " + apiResult.getMsg());
	}

	/**
	 * 在线全推
	 */
	@PostMapping("/online/all")
	@ApiOperation(value = "在线全推", notes = "推送到全部客户端")
	@ApiOperationSupport(order = 2)
	public R<?> pushAllOnline(@RequestBody PushParam param) {
		PushDTO<String> pushDTO = pushService.getPushDTO(param);
		// 进行cid单推
		ApiResult<TaskIdDTO> apiResult = pushApi.pushAll(pushDTO);

		log.info(String.valueOf(apiResult.getData()));
		return R.data("code:" + apiResult.getCode() + ", msg: " + apiResult.getMsg());
	}

	/**
	 * cid推送（支持离线）
	 */
	@PostMapping("/cid")
	@ApiOperation(value = "cid单推", notes = "推送到指定cid的客户端")
	@ApiOperationSupport(order = 3)
	public R<?> pushByCid(@RequestBody PushParam param) {
		//默认使用透传
		ApiResult<?> apiResult = pushService.customPush(param, PushConstant.PUSH_TYPE_CID, 1);
		log.info(String.valueOf(apiResult.getData()));
		return R.data("code:" + apiResult.getCode() + ", msg: " + apiResult.getMsg());
	}

	/**
	 * cid批量推（支持离线）
	 */
	@PostMapping("/list/cid")
	@ApiOperation(value = "cid批量推", notes = "批量推送到指定cid的客户端")
	@ApiOperationSupport(order = 3)
	public R<?> pushListByCid(@RequestBody PushParam param) {
		//默认使用透传
		ApiResult<?> apiResult = pushService.customPush(param, PushConstant.PUSH_TYPE_CIDS, 1);
		log.info(String.valueOf(apiResult.getData()));
		return R.data("code:" + apiResult.getCode() + ", msg: " + apiResult.getMsg());
	}

	/**
	 * 别名推送（支持离线）
	 */
	@PostMapping("/alias")
	@ApiOperation(value = "alias单推", notes = "推送到指定alias的客户端")
	@ApiOperationSupport(order = 4)
	public R<?> pushByAlias(@RequestBody PushParam param) {
		//默认使用透传
		ApiResult<?> apiResult = pushService.customPush(param, PushConstant.PUSH_TYPE_ALIAS, 1);
		log.info(String.valueOf(apiResult.getData()));
		return R.data("code:" + apiResult.getCode() + ", msg: " + apiResult.getMsg());
	}

	/**
	 * 全推（支持离线）
	 */
	@PostMapping("/all")
	@ApiOperation(value = "全推", notes = "推送到指定全部客户端")
	@ApiOperationSupport(order = 3)
	public R<?> pushAll(@RequestBody PushParam param) {
		ApiResult<?> apiResult = pushService.customPush(param, PushConstant.PUSH_TYPE_ALL, 2);
		log.info(String.valueOf(apiResult.getData()));
		return R.data("code:" + apiResult.getCode() + ", msg: " + apiResult.getMsg());
	}

	@PostMapping("/bind")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "将cid和别名绑定", notes = "将cid和别名绑定")
	public R<?> bindAlias(@ApiParam(value = "客户端ID") @RequestParam String clientId,
						  @ApiParam(value = "别名") @RequestParam String alias) {
		CidAliasListDTO cidAliasListDTO = new CidAliasListDTO();
		cidAliasListDTO.add(new CidAliasListDTO.CidAlias(clientId,alias));
		ApiResult<Void> apiResult = userApi.bindAlias(cidAliasListDTO);
		return R.status(apiResult.isSuccess());
	}

	/**
	 * 按天查询推送记录
	 */
	@GetMapping("/queryByDate")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "按天查询推送记录", notes = "按天查询推送记录")
	public ApiResult<?> queryPushResultByDate(@ApiParam(value = "日期") @RequestParam(required = false) String date) {
		// 获取单日推送数据
		return statisticApi.queryPushResultByDate(date);
	}
}
