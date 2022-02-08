import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author zz
 * @date 2021/8/13
 */
@Slf4j
public class MyTest {

	@Data
	class ADT {
		private List<String> BookingCode;
	}

	@Data
	class Price {
		private List<List<ADT>> ADT;
	}

	public static void main(String[] args) {

	}
}




