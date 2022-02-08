package org.fun.test.filter.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wanwan 2021/12/24
 */

//@Component
//@Order(2)
@Slf4j
public class RequestResponseLoggingFilter implements Filter {
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) servletRequest;
		HttpServletResponse res = (HttpServletResponse) servletResponse;
		log.info(
			"RequestResponseLoggingFilter--请求日志  {} : {}", req.getMethod(),
			req.getRequestURI());
		filterChain.doFilter(servletRequest, servletResponse);
		log.info(
			"RequestResponseLoggingFilter--响应日志 :{}",
			res.getContentType());
	}
}
