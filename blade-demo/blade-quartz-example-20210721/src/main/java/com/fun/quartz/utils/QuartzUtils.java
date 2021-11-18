package com.fun.quartz.utils;

import cn.hutool.core.date.DateUtil;
import com.fun.quartz.entity.QuartzJobDto;
import com.fun.quartz.entity.QuartzJobParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author zz
 * @date 2021/7/22
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class QuartzUtils {

	private final Scheduler scheduler;//yml配置

	/**
	 * 创建job
	 *
	 * @param jobClass       具体的定时任务实现类
	 * @param jobName        任务名称(建议唯一)
	 * @param jobGroupName   任务组名
	 * @param cronExpression 时间表达式 （如：0/5 * * * * ? ） 编辑的时候注意格式哟
	 * @param jobData        业务自定义参数
	 */
	public void addJob(Class<? extends QuartzJobBean> jobClass,
					   String jobName,
					   String jobGroupName,
					   String cronExpression,
					   String description,
					   Map jobData) {
		try {
			// 创建jobDetail实例，绑定Job实现类
			JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();
			// 设置job参数
			if (jobData != null && jobData.size() > 0) {
				jobDetail.getJobDataMap().putAll(jobData);
			}
			// 定义调度触发规则
			Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroupName)
				//创建时不自动启动，采用手动启动的方式
				//.startAt(DateBuilder.futureDate(1, DateBuilder.IntervalUnit.SECOND))
				.withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)).withDescription(description).build();
			// 把作业和触发器注册到任务调度中
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (Exception e) {
			log.error("{}创建任务异常 {}", getClass(), e.toString());
		}
	}

	//修改任务
	public String modifyJob(QuartzJobParam appQuartz) throws SchedulerException {
		if (!CronExpression.isValidExpression(appQuartz.getCronExpression())) {
			return "表达式不正确";
		}
		TriggerKey triggerKey = TriggerKey.triggerKey(appQuartz.getJobName(), appQuartz.getJobGroupName());
		JobKey jobKey = new JobKey(appQuartz.getJobName(), appQuartz.getJobGroupName());
		if (scheduler.checkExists(jobKey) && scheduler.checkExists(triggerKey)) {
			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
			//表达式调度构建器,不立即执行
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(appQuartz.getCronExpression()).withMisfireHandlingInstructionDoNothing();
			//按新的cronExpression表达式重新构建trigger
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
				.withSchedule(scheduleBuilder).withDescription(appQuartz.getDescription()).build();

			scheduler.rescheduleJob(triggerKey, trigger);
			return "已修改";
		} else {
			return "任务不存在";
		}

	}

	/**
	 * 删除任务
	 *
	 * @param jobName      任务名称
	 * @param jobGroupName 任务组名
	 */
	public void deleteJob(String jobName, String jobGroupName) throws Exception {
		scheduler.deleteJob(new JobKey(jobName, jobGroupName));
	}


	/**
	 * 获取所有计划中的任务列表
	 */
	public List<QuartzJobDto> queryAllJob() {
		List<QuartzJobDto> jobList = new ArrayList<>();
		try {
			GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
			Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
			for (JobKey jobKey : jobKeys) {
				List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
				for (Trigger trigger : triggers) {
					QuartzJobDto dto = new QuartzJobDto();
					dto.setJobName(jobKey.getName())
						.setJobGroupName(jobKey.getGroup())
						.setJobClass("")
						.setDescription(trigger.getDescription());
					Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
					dto.setJobStatus(triggerState.name());
					if (trigger instanceof CronTrigger) {
						CronTrigger cronTrigger = (CronTrigger) trigger;
						String cronExpression = cronTrigger.getCronExpression();
						dto.setCronExpression(cronExpression);
						dto.setNextTime(DateUtil.formatTime(cronTrigger.getNextFireTime()));
					}
					jobList.add(dto);
				}
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return jobList;
	}

	//暂停任务
	public String pauseJob(String jobName, String jobGroup) throws SchedulerException {
		JobKey jobKey = new JobKey(jobName, jobGroup);
		JobDetail jobDetail = scheduler.getJobDetail(jobKey);
		if (jobDetail == null) {
			return "任务不存在";
		} else {
			scheduler.pauseJob(jobKey);
			return "已暂停";
		}
	}

	// 恢复某个任务
	public String resumeJob(String jobName, String jobGroup) throws SchedulerException {

		JobKey jobKey = new JobKey(jobName, jobGroup);
		JobDetail jobDetail = scheduler.getJobDetail(jobKey);
		if (jobDetail == null) {
			return "任务不存在";
		} else {
			scheduler.resumeJob(jobKey);
			return "已恢复";
		}
	}
}
