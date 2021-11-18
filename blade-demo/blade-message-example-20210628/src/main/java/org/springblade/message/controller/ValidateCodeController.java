package org.springblade.message.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springblade.message.validate.ValidateCodeProcessorHolder;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zz
 * @date 2021/6/23 14:28
 **/
@RestController
@RequiredArgsConstructor
public class ValidateCodeController {

	private final @NonNull ValidateCodeProcessorHolder validateCodeProcessorHolder;

	/**
	 * 通过 type 进行查询到对应的处理器
	 * 同时创建验证码
	 *
	 * @param request  请求
	 * @param response 响应
	 * @param type     验证码类型
	 * @throws Exception 异常
	 */
	@GetMapping("/code/{type}")
	public void createCode(HttpServletRequest request, HttpServletResponse response,
						   @PathVariable String type) throws Exception {
		validateCodeProcessorHolder.findValidateCodeProcessor(type)
			.create(new ServletWebRequest(request, response));
	}

	@PostMapping("auth/sms")
	public HttpEntity<?> sms() {
		return ResponseEntity.ok("ok");
	}
}
