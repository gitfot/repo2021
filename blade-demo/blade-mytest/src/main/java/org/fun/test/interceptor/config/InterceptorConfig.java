package org.fun.test.interceptor.config;

import org.fun.test.interceptor.handler.LogCostInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author wanwan 2021/12/24
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		InterceptorRegistration registration = registry.addInterceptor(new LogCostInterceptor());
		// 拦截配置
		registration.addPathPatterns("/api/**");
		// 排除配置
		registration.excludePathPatterns("/api/word");
		//registry.addInterceptor(new LogCostInterceptor()).addPathPatterns("/**");
	}
}
