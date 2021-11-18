package org.springblade.message.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *
 * @author zz
 * @date 2021/6/24
 */
@Data
@Component
@ConfigurationProperties(prefix = "message.aliyun-sms")
public class SmsProperties {

	private String accessKeyId;

	private String accessKeySecret;

	private String domain;

	private String signName;

	private String templateCode;
}
