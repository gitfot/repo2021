package org.springblade.unipush.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zz
 * @date 2021/7/5
 */
@Data
@Component
@ConfigurationProperties(prefix = "message.unipush")
public class UniPushProperties {

	private String appId;

	private String appSecret;

	private String appKey;

	private String masterSecret;

	private String host;
}
