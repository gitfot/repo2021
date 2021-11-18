package org.springblade.message.validate;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

/**
 * 验证码分发器
 * @author zz
 * @date 2021/6/23 14:30
 **/
@Component
@RequiredArgsConstructor
public class ValidateCodeProcessorHolder {

	private final @NonNull Map<String, org.springblade.message.validate.ValidateCodeProcessor> validateCodeProcessors;

	/**
	 * 通过验证码类型查找
	 *
	 * @param type 验证码类型
	 * @return 验证码处理器
	 */
	public org.springblade.message.validate.ValidateCodeProcessor findValidateCodeProcessor(String type) {
		String name = type.toLowerCase() + org.springblade.message.validate.ValidateCodeProcessor.class.getSimpleName();
		org.springblade.message.validate.ValidateCodeProcessor processor = validateCodeProcessors.get(name);
		if (Objects.isNull(processor)){
			throw new org.springblade.message.validate.ValidateCodeException("验证码处理器" + name + "不存在");
		}
		return processor;
	}
}


