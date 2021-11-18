package org.springblade.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zz
 * @date 2021/7/2
 */
@SpringBootApplication
public class PaymentApplication {

	public static void main(String[] args) {
//		BladeApplication.run("blade-payment", PaymentApplication.class, args);
		SpringApplication.run(PaymentApplication.class, args);
	}
}
