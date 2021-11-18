package org.springblade.mq;

import org.springblade.core.cloud.client.BladeCloudApplication;
import org.springblade.core.launch.BladeApplication;


/**
 * @author zz
 * @date 2021/6/4
 */
@BladeCloudApplication
public class ExampleRabbitMQApplication {

	public static void main(String[] args) {
		BladeApplication.run("blade-test", ExampleRabbitMQApplication.class, args);
	}
}
