package org.fun.test.common.mapper;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author wanwan 2021/11/4
 */
public class UserDao implements BeanNameAware, BeanFactoryAware, ApplicationContextAware, InitializingBean,
	DisposableBean {

	private String userName;
	private int count = 0;

	public String getUserName() {
		return userName;
	}

	//2.属性注入，注入属性为userName
	public void setUserName(String userName) {
		count++;
		System.out.println(count + "：注入属性userName="+userName);
		this.userName = userName;
	}

	//1.无参构造函数，实例化时调用该构造函数
	public UserDao() {
		count++;
		System.out.println(count + "：调用构造函数UserDao()");
	}

	//3.实现BeanNameAware，获取bean id
	public void setBeanName(String s) {
		count++;
		System.out.println(count + "：调用setBeanName()获取bean id,bean id=" + s);
	}

	//4.实现BeanFactoryAware,获取bean工厂
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		count++;
		System.out.println(count + "：调用setBeanFactory()获取bean工厂,beanFactory=" + beanFactory);
	}

	//5.实现ApplicationContextAware,获取bean上下文
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		count++;
		System.out.println(count + "：调用setApplicationContext()获取bean上下文,applicationContext=" + applicationContext);
	}

	//6.实现InitializingBean，获取afterPropertiesSet
	public void afterPropertiesSet() throws Exception {
		count++;
		System.out.println(count + "：调用afterPropertiesSet()");
	}

	//7.自定义初始化方法myInit()
	public void myInit() {
		count++;
		System.out.println(count + "：调用自定义myInit()");
	}

	//8.实现DisposableBean，获取destroy()
	public void destroy() throws Exception {
		count++;
		System.out.println(count + "：destroy()");
	}

	//9.自定义销毁方法myDestroy()
	public void myDestroy() {
		count++;
		System.out.println(count + "：调用自定义destroy()");
	}
}
