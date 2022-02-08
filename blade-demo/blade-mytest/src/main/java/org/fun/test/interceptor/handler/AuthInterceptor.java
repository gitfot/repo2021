package org.fun.test.interceptor.handler;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wanwan 2021/12/24
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String requestUrl = request.getRequestURI();
		if (checkAuth(requestUrl)) {
			return true;
		}

		return false;
	}

	private boolean checkAuth(String requestUrl) {
		System.out.println("===权限校验===");
		return true;
	}
}
