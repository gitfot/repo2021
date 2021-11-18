package org.springblade.jpush.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *
 * @author zz
 * @date 2021/6/29
 */
@Data
@Component
@ConfigurationProperties(prefix = "message.jpush")
public class JpushProperties {

	/**
	 * 极光推送的用户名
	 */
	private String appKey;
	/**
	 * 极光推送的密码
	 */
	private String masterSecret;
	/**
	 * 极光推送设置过期时间
	 */
	private String liveTime;

}
