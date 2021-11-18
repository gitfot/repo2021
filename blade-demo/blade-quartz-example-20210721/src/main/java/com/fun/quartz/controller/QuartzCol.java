package com.fun.quartz.controller;

import com.alibaba.fastjson.JSON;
import com.fun.common.utils.result.Result;
import com.fun.common.utils.result.ResultUtil;
import com.fun.quartz.entity.QuartzJobParam;
import com.fun.quartz.utils.QuartzUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author zz
 * @date 2021/7/22
 */
@RestController
@RequestMapping("/api/quartz")
@RequiredArgsConstructor
@Slf4j
public class QuartzCol {

	private final QuartzUtils quartzUtils;

	/**
	 * 列表
	 */
	@PostMapping(value = "list")
	public Result<?> list() {
		return ResultUtil.success(quartzUtils.queryAllJob());
	}

	/**
	 * 保存
	 */
	@PostMapping(value = "save")
	public Result<?> save(@RequestBody @Valid QuartzJobParam param) {
		String msg;
		try {
			log.info("=========开始创建任务=========");

			//jobClass 也就是具体的实现类例如：com.word.task.FoundEditorTask
			Class clazz = Class.forName(param.getJobClass());
			QuartzJobBean bean = (QuartzJobBean) clazz.newInstance();
			quartzUtils.addJob(bean.getClass(),
				param.getJobName(), param.getJobGroupName(),
				param.getCronExpression(), param.getDescription(),
				null);

			log.info("=========创建任务成功，任务参数{}", JSON.toJSONString(param));
			return ResultUtil.success();

		} catch (ClassNotFoundException e) {
			log.error("{}没有找到任务类{}", getClass(), param.getJobClass());
			msg = String.format("没有找到任务类{}", param.getJobClass());
		} catch (IllegalAccessException | InstantiationException e2) {
			log.error("{}加载异常", param.getJobClass());
			msg = String.format("{}加载异常", param.getJobClass());
		}
		return new Result<>(500, msg);
	}

	/**
	 * 修改
	 */
	@PostMapping(value = "update")
	public Result<?> update(@RequestBody @Valid QuartzJobParam param) {
		try {
			return ResultUtil.sendSuccessMessage(quartzUtils.modifyJob(param));
		} catch (SchedulerException e) {
			log.error("{}SchedulerException{}", getClass(), e.toString());
			return ResultUtil.sendErrorMessage(param.getJobClass() + "SchedulerException");
		}
	}

	/**
	 * 删除任务
	 */
	@PostMapping(value = "deleteJob")
	public Result<?> deleteJob(@RequestBody @Valid QuartzJobParam param) {
		try {
			quartzUtils.deleteJob(param.getJobName(), param.getJobGroupName());
			return ResultUtil.success();
		} catch (Exception e) {
			log.error("{}创建任务异常 {}", getClass(), e.toString());
			return ResultUtil.sendErrorMessage("创建任务异常 {}" + getClass());
		}
	}

	/**
	 * 暂停任务
	 */
	@PostMapping(value = "stopJob")
	public Result<?> stopJob(@RequestBody @Valid QuartzJobParam param) {
		try {
			return ResultUtil.sendSuccessMessage(quartzUtils.pauseJob(param.getJobName(), param.getJobGroupName()));
		} catch (SchedulerException e) {
			return ResultUtil.sendErrorMessage("暂停异常");
		}
	}

	/**
	 * 恢复任务
	 */
	@PostMapping(value = "resumeJob")
	public Result<?> resumeJob(@RequestBody @Valid QuartzJobParam param) {
		try {
			return ResultUtil.sendSuccessMessage(quartzUtils.resumeJob(param.getJobName(), param.getJobGroupName()));
		} catch (SchedulerException e) {
			return ResultUtil.sendErrorMessage("恢复异常");
		}
	}
}
