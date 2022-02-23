package com.fun.pro.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * rabbitMQ 交换机与队列相关配置
 * @author zz
 * @date 2021/6/4
 */
@Component
public class ExchangeQueueConfig2 {

	public static final String ACT_UPDATE_INFO_QUEUE = "act-update-info-queue";

	public static final String ACT_UPDATE_INFO_EXCHANGE = "act-update-info-exchange";

	public static final String ACT_UPDATE_INFO_ROUTING_KEY = "act-update-info-key";

	public static final String DL_GENERAL_QUEUE = "dl_act-update-info-queue";

	public static final String DL_GENERAL_EXCHANGE = "dl_act-update-info-exchange";

	public static final String DL_GENERAL_ROUTING_KEY = "dl_act-update-info-key";

	@Bean(name = "actInfoQueue")
	public Queue actInfoQueue() {
		Map<String, Object> params = new HashMap<>(3);
		// x-dead-letter-exchange 声明了当前队列绑定的死信交换机
		params.put("x-dead-letter-exchange", ACT_UPDATE_INFO_EXCHANGE);
		// x-dead-letter-routing-key 声明了这些死信在转发时携带的 routing-key 名称。
		params.put("x-dead-letter-routing-key", ACT_UPDATE_INFO_ROUTING_KEY);
		// x-message-ttl 队列过期时间（20s）
		params.put("x-message-ttl", 20000);
		return new Queue(ACT_UPDATE_INFO_QUEUE, true, false, false, params);
	}

	@Bean(name = "actInfoExchange")
	public DirectExchange actInfoExchange() {
		return new DirectExchange(ACT_UPDATE_INFO_EXCHANGE);
	}

	@Bean
	Binding directBinding(@Qualifier("actInfoQueue") Queue actInfoQueue,
						  @Qualifier("actInfoExchange") DirectExchange actInfoExchange) {
		return BindingBuilder.bind(actInfoQueue).to(actInfoExchange).with(ACT_UPDATE_INFO_ROUTING_KEY);
	}

	/**
	 * 死信队列
	 */
	@Bean(name = "actInfoDeadLetterQueue")
	public Queue actInfoDeadLetterQueue() {
		return new Queue(DL_GENERAL_QUEUE, true);
	}

	/**
	 * 将路由键和某模式进行匹配。此时队列需要绑定要一个模式上。
	 * 符号“#”匹配一个或多个词，符号“*”匹配不多不少一个词。因此“audit.#”能够匹配到“audit.irs.corporate”，但是“audit.*” 只会匹配到“audit.irs”。
	 **/
	@Bean(name = "actInfoDeadLetterExchange")
	public TopicExchange actInfoDeadLetterExchange() {
		//创建交换机时，可选持久化和是否自动删除，默认为false即可
		return new TopicExchange(DL_GENERAL_EXCHANGE);
	}

	@Bean
	Binding orderTopicBinding(@Qualifier("actInfoDeadLetterQueue") Queue actInfoDeadLetterQueue,
							  @Qualifier("actInfoDeadLetterExchange") TopicExchange actInfoDeadLetterExchange) {
		return BindingBuilder.bind(actInfoDeadLetterQueue).to(actInfoDeadLetterExchange).with(DL_GENERAL_ROUTING_KEY);
	}

}
