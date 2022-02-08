package com.fun.redlock.example;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author wanwan 2022/1/13
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class RedisTemplateExample {

	private final RedisTemplate redisTemplate;

	 /****************bound操作方法***************/
	 //获取了一个指定操作对象（key）的operator，在一个连接（事务）内只能操作这个key对应的value

	/**
	 * String操作
	 */
	public void setString() {
		redisTemplate.boundValueOps("testValue1").set("value1",60, TimeUnit.SECONDS); //设置过期时间为60秒，60秒后自动删除
		redisTemplate.boundValueOps("testValue2").set("value2");
        redisTemplate.boundValueOps("testValue2").set("22222",3); //把testValue2的前三位置换为22222
	}

	public void getString() {
		Object o1 = redisTemplate.boundValueOps("testValue1").get();
		Object o2 = redisTemplate.boundValueOps("testValue2").get();
		System.out.println(o1 + " "  + o2);
	}

	/**
	 * list操作
	 */
	public void setList() {
		redisTemplate.boundListOps("list").leftPush("aaa");
		redisTemplate.boundListOps("list").leftPush("aaa");
		redisTemplate.boundListOps("list").leftPush("aaa");
		redisTemplate.boundListOps("list").leftPush("bbb");
		redisTemplate.boundListOps("list").rightPush("ccc");
		redisTemplate.boundListOps("list").rightPush("ddd");

		redisTemplate.boundListOps("list").remove(2,"aaa"); //删除两个个aaa
		redisTemplate.boundListOps("list").expire(60,TimeUnit.SECONDS); //设置60秒后过期
	}

	public void getList() {
		List list = redisTemplate.boundListOps("list").range(0,10); //查询，range(0,10)会查询出0-10的元素
		System.out.println(list);
		System.out.println(redisTemplate.boundValueOps("list").getKey()); //获取key值

		System.out.println(redisTemplate.boundListOps("list").index(1)); //根据索引获取值
		System.out.println(redisTemplate.boundListOps("list").leftPop()); //bbb,打印左边起第一个元素值
		redisTemplate.delete("list"); //删除整个集合
	}

	/**
	 * 操作hash
	 */
	public void setHash() {
		redisTemplate.boundHashOps("hash").put("1", "a");
		redisTemplate.boundHashOps("hash").put("2", "b");
		redisTemplate.boundHashOps("hash").put("3", "c");
		redisTemplate.boundHashOps("hash").put("4", "d");

	}

	public void getHash() {
		List hash = redisTemplate.boundHashOps("hash").values();
		System.out.println(hash);

		Set set = redisTemplate.boundHashOps("hash").keys();
		System.out.println(set);

		Object o = redisTemplate.boundHashOps("hash").get("1");
		System.out.println(o);
	}



}
