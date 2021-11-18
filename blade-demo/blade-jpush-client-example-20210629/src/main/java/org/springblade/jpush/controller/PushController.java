package org.springblade.jpush.controller;

import lombok.RequiredArgsConstructor;
import org.springblade.jpush.service.PushService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author zz
 * @date 2021/6/30
 */
@RestController
@RequestMapping("/push")
@RequiredArgsConstructor
public class PushController {

	private final PushService pushService;

	@PostMapping("/test")
	public void testSend() {
		//设置客户端tag
		//pushService.updateDevice();
		//发送到指定tag的客户端
		pushService.testSendMessage();
	}

}
