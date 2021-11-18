package org.springblade.unipush;

import org.springblade.core.cloud.client.BladeCloudApplication;
import org.springblade.core.launch.BladeApplication;

/**
 * @author zz
 * @date 2021/7/4
 */

@BladeCloudApplication
public class UnipushApplication {

	public static void main(String[] args) {
		BladeApplication.run("blade-unipush", UnipushApplication.class, args);
	}
}
