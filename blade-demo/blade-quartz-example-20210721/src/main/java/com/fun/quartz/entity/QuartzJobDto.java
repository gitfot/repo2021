package com.fun.quartz.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author zz
 * @date 2021/7/22
 */
@Data
@Accessors(chain = true)
public class QuartzJobDto {

	/**
	 * 任务名称
	 */
	private String jobGroupName;

	/**
	 * 任务名称
	 */
	private String jobName;

	/**
	 * 任务执行类
	 */
	private String jobClass;

	/**
	 * 任务状态 启动还是暂停
	 */
	private String jobStatus;

	/**
	 * 任务运行时间表达式
	 */
	private String cronExpression;

	/**
	 * 下次执行时间
	 */
	private String nextTime;

	/**
	 * 描述
	 */
	private String description;
}
