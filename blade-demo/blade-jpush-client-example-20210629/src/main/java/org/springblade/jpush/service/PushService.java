package org.springblade.jpush.service;

import lombok.RequiredArgsConstructor;
import org.springblade.jpush.util.JpushUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author zz
 * @date 2021/6/30
 */
@Service
public class PushService {

	@Autowired
	private JpushUtils jpushUtils;

	public void updateDevice() {
		Set<String> tags = new HashSet<>();
		tags.add("test");
		String alias = "zhan";
		try {
			//设置标签
			//jpushUtils.updateDeviceTagAlias("13065ffa4ed91da7f86",null, tags, null);
			//jpushUtils.updateDeviceTagAlias("13065ffa4edee3bac11",null, tags, null);
			//设置别名
			jpushUtils.updateDeviceTagAlias("13065ffa4edee3bac11",alias, null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testSendMessage() {
		String title = "halo";
		String[] rid = new String[] {"13065ffa4ed91da7f86","13065ffa4edee3bac11"};
		//标签
		String[] tags = new String[]{"test"};
		//别名
		String[] alias = new String[] {"zhan"};
		//其他消息，不会在通知栏显示
		Map<String,String> map = new HashMap<>();
		map.put("message","其他的message消息");
		try {
			//全平台发送
			String content0 = "你好，这是一安卓iOS推送测试！";
			jpushUtils.pushAll(title, content0, map);

			//tag发送测试
			String content1 = "你好，这是一条tag推送测试！";
			//jpushUtils.pushByTags(title, content1, map, tags);

			//alias发送测试
			String content2 = "你好，这是一条alias推送测试！";
			//jpushUtils.pushByAlias(title, content2, map, alias);

			//registrationId发送测试
			String content3 = "你好，这是一条rid推送测试！";
			//jpushUtils.pushByRID(title, content3, map, rid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
