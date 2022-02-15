import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author zz
 * @date 2021/8/13
 */
@Slf4j
public class MyTest {

	@Test
	public void test1() {
		boolean b = CharMatcher.anyOf("x×*").matchesAnyOf("20x20");
		Iterable<String> split2 = Splitter.onPattern("x|×|\\*")
			.trimResults()
			.omitEmptyStrings()
			.split("20文字");
		ArrayList<String> list = Lists.newArrayList(split2);
		System.out.println(list);
	}

}
