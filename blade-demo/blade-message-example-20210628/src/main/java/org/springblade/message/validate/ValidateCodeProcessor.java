package org.springblade.message.validate;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码处理器
 * @author zz
 * @date 2021/6/22
 */
public interface ValidateCodeProcessor {
	/**
	 * 创建验证码
	 *
	 * @param request 请求
	 * @throws Exception 异常
	 */
	void create(ServletWebRequest request) throws Exception;

	/**
	 * 验证验证码
	 *
	 * @param request 请求
	 */
	void validate(ServletWebRequest request);

}
