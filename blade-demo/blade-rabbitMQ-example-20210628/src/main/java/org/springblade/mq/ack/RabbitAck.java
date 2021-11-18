package org.springblade.mq.ack;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import javax.annotation.PostConstruct;

/**
 * @Description
 * @Author zz
 * @Date 2021/6/4
 */
@RequiredArgsConstructor
public class RabbitAck implements RabbitTemplate.ConfirmCallback{

	private final static Logger logger = LoggerFactory.getLogger(RabbitAck.class);

	private final RabbitTemplate rabbitTemplate;

	@PostConstruct
	public void init() {
		//rabbitTemplate如果为单例的话，那回调就是最后设置的内容
		rabbitTemplate.setConfirmCallback(this);
	}

	/**
	 * 回调方法
	 */
	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		logger.info("ACK --- MQ message id: {}" + correlationData.getId());
		if (ack) {
			logger.info("ACK --- Message sent confirmation success！");
		} else {
			logger.info("ACK --- MQ message id: {}", correlationData.getId());
			logger.info("ACK --- MQ confirmation: {}", ack);
			logger.info("ACK --- Message sending confirmation failed, reason for failure:" + cause);
		}
	}
}
