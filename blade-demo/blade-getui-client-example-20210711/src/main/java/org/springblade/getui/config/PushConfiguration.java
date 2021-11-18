package org.springblade.getui.config;

import com.getui.push.v2.sdk.ApiHelper;
import com.getui.push.v2.sdk.GtApiConfiguration;
import com.getui.push.v2.sdk.api.PushApi;
import com.getui.push.v2.sdk.api.StatisticApi;
import com.getui.push.v2.sdk.api.UserApi;
import com.getui.push.v2.sdk.dto.req.message.PushChannel;
import com.getui.push.v2.sdk.dto.req.message.android.AndroidDTO;
import com.getui.push.v2.sdk.dto.req.message.android.ThirdNotification;
import com.getui.push.v2.sdk.dto.req.message.android.Ups;
import com.getui.push.v2.sdk.dto.req.message.ios.Alert;
import com.getui.push.v2.sdk.dto.req.message.ios.Aps;
import com.getui.push.v2.sdk.dto.req.message.ios.IosDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zz
 * @date 2021/7/11
 */
@Configuration
@RequiredArgsConstructor
public class PushConfiguration {

	public final UniPushProperties uniPushProperties;

	@Bean
	public PushApi getPushApi() {
		GtApiConfiguration apiConfiguration = new GtApiConfiguration();
		//填写应用配置
		apiConfiguration.setAppId(uniPushProperties.getAppId());
		apiConfiguration.setAppKey(uniPushProperties.getAppKey());
		apiConfiguration.setMasterSecret(uniPushProperties.getMasterSecret());
		// 接口调用前缀，请查看文档: 接口调用规范 -> 接口前缀, 可不填写appId
		apiConfiguration.setDomain(uniPushProperties.getHost());
		// 实例化ApiHelper对象，用于创建接口对象
		ApiHelper apiHelper = ApiHelper.build(apiConfiguration);
		// 创建对象，建议复用。目前有PushApi、StatisticApi、UserApi
		return apiHelper.creatApi(PushApi.class);
	}

	@Bean
	public UserApi getUserApi() {
		GtApiConfiguration apiConfiguration = new GtApiConfiguration();
		//填写应用配置
		apiConfiguration.setAppId(uniPushProperties.getAppId());
		apiConfiguration.setAppKey(uniPushProperties.getAppKey());
		apiConfiguration.setMasterSecret(uniPushProperties.getMasterSecret());
		// 接口调用前缀，请查看文档: 接口调用规范 -> 接口前缀, 可不填写appId
		apiConfiguration.setDomain(uniPushProperties.getHost());
		// 实例化ApiHelper对象，用于创建接口对象
		ApiHelper apiHelper = ApiHelper.build(apiConfiguration);
		// 创建对象，建议复用。目前有PushApi、StatisticApi、UserApi
		return apiHelper.creatApi(UserApi.class);
	}

	@Bean
	public StatisticApi getStatisticApi() {
		GtApiConfiguration apiConfiguration = new GtApiConfiguration();
		//填写应用配置
		apiConfiguration.setAppId(uniPushProperties.getAppId());
		apiConfiguration.setAppKey(uniPushProperties.getAppKey());
		apiConfiguration.setMasterSecret(uniPushProperties.getMasterSecret());
		// 接口调用前缀，请查看文档: 接口调用规范 -> 接口前缀, 可不填写appId
		apiConfiguration.setDomain(uniPushProperties.getHost());
		// 实例化ApiHelper对象，用于创建接口对象
		ApiHelper apiHelper = ApiHelper.build(apiConfiguration);
		// 创建对象，建议复用。目前有PushApi、StatisticApi、UserApi
		return apiHelper.creatApi(StatisticApi.class);
	}

}
