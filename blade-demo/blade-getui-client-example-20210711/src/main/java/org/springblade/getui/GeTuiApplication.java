package org.springblade.getui;

import org.springblade.core.cloud.client.BladeCloudApplication;
import org.springblade.core.launch.BladeApplication;

/**
 * @author zz
 * @date 2021/7/11
 */
@BladeCloudApplication
public class GeTuiApplication {

	public static void main(String[] args) {
		BladeApplication.run("blade-push", GeTuiApplication.class, args);
	}
}
