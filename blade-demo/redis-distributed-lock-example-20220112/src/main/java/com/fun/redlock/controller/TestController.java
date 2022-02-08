package com.fun.redlock.controller;

import com.fun.redlock.util.RedisUtil;
import com.fun.redlock.util.RedisUtil2;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wanwan 2022/1/20
 */
@RestController
@RequiredArgsConstructor
public class TestController {

	private final RedisUtil<Object> redisUtil;
	private final RedisUtil2 redisUtil2;

	@GetMapping("/test1")
	public void test1() {
		redisUtil.cacheValue("hello","test",10);
		redisUtil2.set("hello2","test2",10);
	}
}
