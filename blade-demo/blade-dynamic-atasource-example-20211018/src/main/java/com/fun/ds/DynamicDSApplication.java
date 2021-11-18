package com.fun.ds;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wanwan 2021/10/18
 */
@SpringBootApplication
@MapperScan(basePackages = "com.fun.ds.mapper")
public class DynamicDSApplication {

	public static void main(String[] args) {
		SpringApplication.run(DynamicDSApplication.class, args);
	}
}
