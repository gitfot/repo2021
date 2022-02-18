package com.fun.pro.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wanwan 2022/2/18
 */
@Configuration
public class ExchangeQueueConfigTmp {

	@Bean
	public Queue topicQueue() {
		// 队列持久化
		return new Queue("canal_001_queue", true);
	}

	@Bean
	public TopicExchange topicExchange() {
		//创建交换机时，可选持久化和是否自动删除，默认为false即可
		return new TopicExchange("canal_001_exchange");
	}

	@Bean
	Binding topicBinding(@Qualifier("topicQueue") Queue queue,
						 @Qualifier("topicExchange") TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with("canal_001_routingKey");
	}
}
