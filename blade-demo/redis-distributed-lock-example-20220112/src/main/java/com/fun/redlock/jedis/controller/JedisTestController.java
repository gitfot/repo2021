package com.fun.redlock.jedis.controller;

import com.fun.redlock.jedis.annotation.JLock;
import com.fun.redlock.jedis.service.JedisLockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Executor;

/**
 * @author wanwan 2022/1/13
 */
@RestController
@RequestMapping("/jedis")
@RequiredArgsConstructor
public class JedisTestController {

	private final Executor executor;
	private final JedisLockService jedisLockService;

	/**
	 * 可重入锁测
	 */
	@GetMapping("/test1")
	public void test1() throws InterruptedException {
		executor.execute(jedisLockService::buzWithJedisLuaLock);
//		Thread.sleep(4000);
		executor.execute(jedisLockService::buzWithJedisLuaLock);
//		jedisLockService.buzWithJedisLuaLock();
	}


	/**
	 * 注解锁
	 */
	@GetMapping("/test2")
	@JLock
	public void test2() throws InterruptedException {
		jedisLockService.testAnnotationLock();
	}
}
