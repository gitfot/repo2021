package org.springblade.message.validate;

/**
 * 验证码验证错误异常
 * @author zz
 * @date 2021/6/24
 */
public class ValidateCodeException extends RuntimeException {
	public ValidateCodeException(String message) {
		super(message);
	}
}
