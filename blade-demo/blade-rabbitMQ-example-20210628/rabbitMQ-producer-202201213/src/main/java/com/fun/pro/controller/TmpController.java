package com.fun.pro.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wanwan 2022/2/18
 */
@RestController
@RequestMapping("/tmp")
@RequiredArgsConstructor
public class TmpController {

	private final RabbitTemplate rabbitTemplate;

	@PostMapping("/send-topic")
	public void test1() {
		rabbitTemplate.convertAndSend("canal_001_exchange","canal_001_routingKey","123");
	}
}
