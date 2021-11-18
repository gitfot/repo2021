package org.fun.test.common.handler;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author wanwan 2021/11/4
 */
public  class MyBeanPostProcessor implements BeanPostProcessor {
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("=====调用postProcessBeforeInitialization()=====");
		return bean;
	}

	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("=====调用postProcessAfterInitialization()=====");
		return bean;
	}

}
