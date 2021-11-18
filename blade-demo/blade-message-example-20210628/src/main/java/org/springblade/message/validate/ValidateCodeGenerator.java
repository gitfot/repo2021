package org.springblade.message.validate;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码生成
 *
 */
public interface ValidateCodeGenerator {
    /**
     * 生成验证码
     *
     * @param request 请求
     * @return 生成结果
     */
    String generate(ServletWebRequest request);
}
