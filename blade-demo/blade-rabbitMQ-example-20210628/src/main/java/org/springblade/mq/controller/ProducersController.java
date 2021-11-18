package org.springblade.mq.controller;

import org.springblade.mq.send.Sender;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zz
 * @date 2021/6/4
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/test")
public class ProducersController {

	private final Sender sender;

	/**
	 * 简单模式测试
	 */
	@PostMapping("/produce")
	public void producers(){
		sender.sendSimple("Hello World");
	}

	/**
	 * Work模式测试
	 */
	@PostMapping("/batch/producers")
	public void batchProducers(){
		for (int i = 0; i < 20; i++){
			try {
				Thread.sleep(1500L);
				sender.sendSimple("Hello World" + i);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * direct模式
	 */
	@PostMapping("/testSendQueue")
	public void testSendQueue() {
		sender.sendDirect("direct模式测试，指定routingKey为'test'");
	}

	/**
	 * topic模式
	 */
	@PostMapping("/topicSendQueue")
	public void topicSendQueue() {
		sender.sendTopic("topic模式测试，指定routingKey为'blue.sky'");
	}

	/**
	 * 发送信息
	 */
	@PostMapping("/delaySend")
	public void sendDelayMessage(){
		sender.sendDelay("某某某订单已经失效，请归还库存");
	}
}
