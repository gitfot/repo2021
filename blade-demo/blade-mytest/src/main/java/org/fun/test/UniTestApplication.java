package org.fun.test;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zz
 * @date 2021/7/30
 */
//@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@SpringBootApplication
@MapperScan({"org.fun.test.handset","org.fun.test.order"})
public class UniTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(UniTestApplication.class, args);
	}

}
