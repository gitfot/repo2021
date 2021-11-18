import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.junit.Test;

import java.time.LocalDateTime;


/**
 * @author zz
 * @date 2021/7/19
 */

public class TestGen {

	/**
	 * 生成 activiti的数据库表
	 */
	@Test
	public void testCreateDbTable() {
	//使用classpath下的activiti.cfg.xml中的配置创建processEngine
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		System.out.println(processEngine);
	}

	@Test
	public void test2() {
		LocalDateTime dateTime = LocalDateTime.now();
		System.out.println(dateTime); // 2021-06-12T16:54:19.254

	}
}
