package org.springblade.mq.config;

import org.springblade.mq.constant.RabbitMqKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * rabbitMQ配置类
 * @author zz
 * @date 2021/6/4
 */
@Component
public class TradeOrderQueueConfig {

	private final static Logger logger = LoggerFactory.getLogger(TradeOrderQueueConfig.class);

	/**
	 * 创建队列
	 * Queue 可以有4个参数
	 * String name: 队列名
	 * boolean durable: 持久化消息队列，rabbitmq 重启的时候不需要创建新的队列，默认为 true
	 * boolean exclusive: 表示该消息队列是否只在当前的connection生效，默认为 false
	 * boolean autoDelete: 表示消息队列在没有使用时将自动被删除，默认为 false
	 * Map<String, Object> arguments:
	 */
	@Bean(name = "generalQueue")
	public Queue queue() {
		logger.info("queue : {}", RabbitMqKey.GENERAL_QUEUE);
		// 队列持久化
		return new Queue(RabbitMqKey.GENERAL_QUEUE, true);
	}

	/**
	 * 创建一个 Fanout 类型的交换器
	 * <p>
	 * rabbitmq中，Exchange 有4个类型：Direct，Topic，Fanout，Headers
	 * Direct Exchange：将消息中的Routing key与该Exchange关联的所有Binding中的Routing key进行比较，如果相等，则发送到该Binding对应的Queue中；
	 * Topic Exchange：将消息中的Routing key与该Exchange关联的所有Binding中的Routing key进行对比，如果匹配上了，则发送到该Binding对应的Queue中；
	 * Fanout Exchange：直接将消息转发到所有binding的对应queue中，这种exchange在路由转发的时候，忽略Routing key；
	 * Headers Exchange：将消息中的headers与该Exchange相关联的所有Binging中的参数进行匹配，如果匹配上了，则发送到该Binding对应的Queue中；
	 */
	@Bean(name = "fanoutExchange")
	public FanoutExchange fanoutExchange() {
		logger.info("exchange : {}", RabbitMqKey.FANOUT_EXCHANGE);
		return new FanoutExchange(RabbitMqKey.FANOUT_EXCHANGE);
	}

	/**
	 * 把队列（Queue）绑定到交换器（Exchange）
	 * topic 使用路由键（routingKey）
	 */
	@Bean
	Binding fanoutBinding(@Qualifier("generalQueue") Queue queue,
						  @Qualifier("fanoutExchange") FanoutExchange fanoutExchange) {
		return BindingBuilder.bind(queue).to(fanoutExchange);
	}

	/*===================DIRECT MODE=======================*/

	@Bean
	public Queue directQueue() {
		logger.info("queue : {}", RabbitMqKey.TRADE_DIRECT_QUEUE);
		// 队列持久化
		return new Queue(RabbitMqKey.TRADE_DIRECT_QUEUE, true);
	}

	@Bean
	public DirectExchange directExchange() {
		logger.info("exchange : {}", RabbitMqKey.TRADE_DIRECT_EXCHANGE);
		return new DirectExchange(RabbitMqKey.TRADE_DIRECT_EXCHANGE);
	}

	@Bean
	Binding directBinding(@Qualifier("directQueue") Queue directQueue,
						  @Qualifier("directExchange") DirectExchange directExchange) {
		return BindingBuilder.bind(directQueue).to(directExchange).with(RabbitMqKey.DIRECT_ROUTING_KEY);
	}

	/*=================TOPIC MODE================*/

	@Bean
	public Queue topicQueue() {
		logger.info("queue : {}", RabbitMqKey.TRADE_TOPIC_QUEUE);
		// 队列持久化
		return new Queue(RabbitMqKey.TRADE_TOPIC_QUEUE, true);
	}

	@Bean
	public TopicExchange topicExchange() {
		logger.info("exchange : {}", RabbitMqKey.TRADE_TOPIC_EXCHANGE);
		return new TopicExchange(RabbitMqKey.TRADE_TOPIC_EXCHANGE);
	}

	@Bean
	Binding topicBinding(@Qualifier("topicQueue") Queue queue,
						  @Qualifier("topicExchange") TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(RabbitMqKey.TOPIC_ROUTING_KEY);
	}

	/*===============DEAD LETTER QUEUE===============*/
	/**
	 * 接收延迟信息的队列，并指定过期时间，以及过期之后要发送到哪个死信交换器，以及死信交换器的路由
	 */
	@Bean(name = "delayQueue")
	public Queue delayQueue() {
		Map<String, Object> params = new HashMap<>(3);
		// x-dead-letter-exchange 声明了当前队列绑定的死信交换机
		params.put("x-dead-letter-exchange", RabbitMqKey.TRADE_DEAD_LETTER_EXCHANGE);
		// x-dead-letter-routing-key 声明了这些死信在转发时携带的 routing-key 名称。
		params.put("x-dead-letter-routing-key", RabbitMqKey.DEAD_LETTER_ROUTING_KEY);
		// x-message-ttl 队列过期时间（20s）
		params.put("x-message-ttl", 20000);
		return QueueBuilder.durable(RabbitMqKey.TRADE_DELAY_QUEUE).withArguments(params).build();
	}

	/**
	 * 接收延迟信息的交换器
	 */
	@Bean(name = "delayExchange")
	public DirectExchange delayExchange() {
		return new DirectExchange(RabbitMqKey.TRADE_DELAY_EXCHANGE);
	}

	@Bean
	Binding delayBinding(@Qualifier("delayQueue") Queue delayQueue,
							  @Qualifier("delayExchange") DirectExchange delayExchange) {
		return BindingBuilder.bind(delayQueue).to(delayExchange).with(RabbitMqKey.DELAY_ROUTING_KEY);
	}

	/**
	 * 死信队列
	 */
	@Bean(name = "deadLetterQueue")
	public Queue deadLetterQueue() {
		return new Queue(RabbitMqKey.TRADE_DEAD_LETTER_QUEUE, true);
	}

	/**
	 * 将路由键和某模式进行匹配。此时队列需要绑定要一个模式上。
	 * 符号“#”匹配一个或多个词，符号“*”匹配不多不少一个词。因此“audit.#”能够匹配到“audit.irs.corporate”，但是“audit.*” 只会匹配到“audit.irs”。
	 **/
	@Bean(name = "deadLetterTopicExchange")
	public TopicExchange deadLetterTopicExchange() {
		return new TopicExchange(RabbitMqKey.TRADE_DEAD_LETTER_EXCHANGE);
	}

	@Bean
	Binding orderTopicBinding(@Qualifier("deadLetterQueue") Queue orderQueue,
							  @Qualifier("deadLetterTopicExchange") TopicExchange orderTopicExchange) {
		return BindingBuilder.bind(orderQueue).to(orderTopicExchange).with(RabbitMqKey.DEAD_LETTER_ROUTING_KEY);
	}

}
