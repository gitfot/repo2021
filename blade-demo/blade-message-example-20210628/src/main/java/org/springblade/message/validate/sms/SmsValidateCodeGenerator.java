package org.springblade.message.validate.sms;

import org.springblade.message.util.RandomCode;
import org.springblade.message.validate.ValidateCodeGenerator;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 手机验证码生成器
 * @author zz
 * @date 2021/6/23 11:33
 **/
@Component
public class SmsValidateCodeGenerator implements ValidateCodeGenerator {

	@Override
	public String generate(ServletWebRequest request) {
		// 定义手机验证码生成策略，可以使用 request 中从请求动态获取生成策略
		// 可以从配置文件中读取生成策略
		return RandomCode.random(4, true);
	}

}
