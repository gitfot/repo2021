package mq.ack;//package org.springblade.mq.ack;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.core.ReturnedMessage;
//import org.springframework.amqp.rabbit.connection.CorrelationData;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//
//import javax.annotation.PostConstruct;
//
///**
// * 配置回调（监听消息是否被正确路由）
// * @author wanwan 2022/2/13
// */
//@Slf4j
//@RequiredArgsConstructor
//public class RabbitAck implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback{
//
//	private final static Logger logger = LoggerFactory.getLogger(RabbitAck.class);
//
//	private final RabbitTemplate rabbitTemplate;
//
//	@PostConstruct
//	public void init() {
//		//可以添加回调，接收并处理返回的消息，默认false
//		rabbitTemplate.setMandatory(true);
//		//rabbitTemplate如果为单例的话，那回调就是最后设置的内容
//		rabbitTemplate.setConfirmCallback(this);
//		rabbitTemplate.setReturnsCallback(this);
//	}
//
//	/**
//	 * 消息是否成功发送到 Exchange
//	 */
//	@Override
//	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
//		logger.info("ACK --- MQ message id: {}" + correlationData.getId());
//		if (ack) {
//			logger.info("ACK --- Message send to exchange success！");
//		} else {
//			logger.info("ACK --- Message send to exchange failed, reason for failure:" + cause);
//		}
//	}
//
//	/**
//	 * 消息是否正确被路由到queue
//	 */
//	@Override
//	public void returnedMessage(ReturnedMessage returnedMessage) {
//		log.warn("ACK --- Message sent to queue fail！--returned：{}", returnedMessage);
//	}
//}
