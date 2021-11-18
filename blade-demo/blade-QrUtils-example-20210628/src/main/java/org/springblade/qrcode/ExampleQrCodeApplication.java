package org.springblade.qrcode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author zz
 * @date 2021/6/28
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ExampleQrCodeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExampleQrCodeApplication.class, args);
	}
}
