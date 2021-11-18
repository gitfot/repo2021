package com.fun.quartz.task;

import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

/**
 * @author zz
 * @date 2021/7/22
 */
@Component
public class AccountTask extends QuartzJobBean {

	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) {
		//todo
		System.out.println(LocalTime.now() + "--job start ....");
	}
}
