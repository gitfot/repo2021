package org.springblade.jpush;

import org.springblade.core.cloud.client.BladeCloudApplication;
import org.springblade.core.launch.BladeApplication;

/**
 *
 * @author zz
 * @date 2021/6/22
 */
@BladeCloudApplication
public class ExampleJpushApplication {

	public static void main(String[] args) {
		BladeApplication.run("example-jpushClient", ExampleJpushApplication.class, args);
	}
}
