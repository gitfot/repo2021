package org.springblade.test;

import org.springblade.core.cloud.client.BladeCloudApplication;
import org.springblade.core.launch.BladeApplication;

/**
 * @author zz
 * @date 2021/6/28
 */
@BladeCloudApplication
public class TestApplication {

	public static void main(String[] args) {
		BladeApplication.run("blade-test", TestApplication.class, args);
	}
}
