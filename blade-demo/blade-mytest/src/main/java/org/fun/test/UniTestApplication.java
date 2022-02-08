package org.fun.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author zz
 * @date 2021/7/30
 */
//@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@SpringBootApplication
@EnableAsync
public class UniTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(UniTestApplication.class, args);
	}

}
