package org.fun.test.interceptor.config;

import org.fun.test.interceptor.handler.AuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author wanwan 2021/12/24
 */
//@Configuration
public class WebAuthConfig extends WebMvcConfigurerAdapter {

	@Bean
	public AuthInterceptor getAuthInterceptor() {
		return new AuthInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new AuthInterceptor());
	}
}

