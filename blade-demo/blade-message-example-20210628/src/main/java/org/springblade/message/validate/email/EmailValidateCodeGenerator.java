package org.springblade.message.validate.email;

import org.springblade.message.util.RandomCode;
import org.springblade.message.validate.ValidateCodeGenerator;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 邮箱验证码生成器
 * @author zz
 * @date 2021/6/23 11:34
 **/
@Component
public class EmailValidateCodeGenerator implements ValidateCodeGenerator {

	@Override
	public String generate(ServletWebRequest request) {
		return RandomCode.random(6);
	}
}
