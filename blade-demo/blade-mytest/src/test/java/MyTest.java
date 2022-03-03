import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author zz
 * @date 2021/8/13
 */
@Slf4j
public class MyTest {

	@Autowired
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;


	@Test
	public void test1() {

	}

}
