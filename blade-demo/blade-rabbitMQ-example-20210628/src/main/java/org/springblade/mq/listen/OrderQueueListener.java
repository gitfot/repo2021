package org.springblade.mq.listen;

import org.springblade.mq.constant.RabbitMqKey;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 队列监听类样例
 * @author zz
 * @date 2021/6/5
 */
@Component
public class OrderQueueListener {
	private static final Logger logger = LoggerFactory.getLogger(OrderQueueListener.class);

	/**
	 * 监听direct队列消息
	 */
	@RabbitListener(queues = RabbitMqKey.TRADE_DIRECT_QUEUE)
	public void process3(Message message) {
		try {
			String msg = new String(message.getBody());
			if (StringUtils.isBlank(msg)) {
				logger.warn("接收的数据为空");
				return;
			}
			System.out.println("服务器收到消息延迟队列消息：" + msg);
		} catch (Exception e) {
			logger.warn("处理接收到数据，发生异常：{}", e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 监听delay队列消息
	 */
	@RabbitListener(queues = RabbitMqKey.TRADE_DELAY_QUEUE)
	public void process2(Message message) {
		String msg = new String(message.getBody());
		if (StringUtils.isBlank(msg)) {
			logger.warn("接收的数据为空");
			return;
		}
		System.out.println(LocalDateTime.now() + "正在重试接收消息：" + msg);
		int a = 1 / 0;
	}

	/**
	 * 接收死信队列的消息
	 */
	@RabbitListener(queues = RabbitMqKey.TRADE_DEAD_LETTER_QUEUE)
	public void process4(Message message) {
		try {
			String msg = new String(message.getBody());
			if (StringUtils.isBlank(msg)) {
				logger.warn("接收的数据为空");
				return;
			}
			System.out.println("服务器收到死信队列消息：" + msg);
		} catch (Exception e) {
			logger.warn("处理接收到数据，发生异常：{}", e.getMessage());
			e.printStackTrace();
		}
	}
}
