package org.fun.test.filter.config;

import com.google.common.collect.Lists;
import org.fun.test.filter.filter.RequestResponseLoggingFilter;
import org.fun.test.filter.filter.TransactionFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wanwan 2021/12/24
 */

@Configuration
public class FilterConfig {

	//@Bean
	public FilterRegistrationBean<TransactionFilter> transActionFilter(){
		FilterRegistrationBean<TransactionFilter> registrationBean = new FilterRegistrationBean<>();

		registrationBean.setFilter(new TransactionFilter());
		registrationBean.setUrlPatterns(Lists.newArrayList("/user/*"));
		registrationBean.setOrder(1);
//		registrationBean.addUrlPatterns("/users/*");
		return registrationBean;
	}

	//@Bean
	public FilterRegistrationBean<RequestResponseLoggingFilter> loggingFilter(){
		FilterRegistrationBean<RequestResponseLoggingFilter> registrationBean = new FilterRegistrationBean<>();

		registrationBean.setFilter(new RequestResponseLoggingFilter());
		registrationBean.setUrlPatterns(Lists.newArrayList("/user/*"));
		registrationBean.setOrder(2);
//		registrationBean.addUrlPatterns("/users/*");
		return registrationBean;
	}
}
