package org.springblade.message.validate.email;

import org.springblade.message.validate.impl.AbstractValidateCodeProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @Description 邮箱验证码处理器
 * @Author zz
 * @Date 2021/6/23 9:33
 **/
@Component
public class EmailValidateCodeProcessor extends AbstractValidateCodeProcessor {

	@Override
	protected void send(ServletWebRequest request, String validateCode) {
		System.out.println(request.getParameter("email") +
			"邮箱验证码发送成功，验证码为：" + validateCode);
	}
}
