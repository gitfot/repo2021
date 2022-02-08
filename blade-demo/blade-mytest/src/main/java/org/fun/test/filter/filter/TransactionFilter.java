package org.fun.test.filter.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author wanwan 2021/12/24
 */
//@Component
//@Order(1)
@Slf4j
public class TransactionFilter implements Filter {

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) servletRequest;
		log.info(
			"TransactionFilter--为请求开启一个事务 : {}",
			req.getRequestURI());

		filterChain.doFilter(servletRequest, servletResponse);
		log.info(
			"TransactionFilter--提交请求事务 : {}",
			req.getRequestURI());
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		//handle
	}

	@Override
	public void destroy() {
		//handle
	}
}
