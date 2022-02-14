import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author zz
 * @date 2021/8/13
 */
@Slf4j
public class MyTest {

	public static void main(String[] args) {
		//treeMap完成自动排序+自然排序
		Map<Object, Object> requestData = new TreeMap<>();
		Map<Object, Object> requestHeader = new TreeMap<>();
		requestHeader.put("inresponseto", "eddf774800a914eda9b701b5c450e217");
		requestHeader.put("resultcode", "104103");
		requestHeader.put("systemtime", "20220211022307694");
		requestHeader.put("version", "1.0");
		//...组装header报文
		Map<Object, Object> requestBody = new TreeMap<>();
		requestData.put("header", requestHeader);
		requestData.put("body", requestBody);
		System.out.println("json串，得到mac:"+hmacsha256(SOURCEKEY, JSON.toJSONString(requestData)));
	}

	private static final String HMAC_ALGORITHM = "HmacSHA256";

	private static final String SOURCEKEY = "123456";
	private static final String SOURCE_ID = "100000";

	public static String hmacsha256(String secret, String data) {
		Mac mac;
		byte[] doFinal;
		try {
			mac = Mac.getInstance(HMAC_ALGORITHM);
			//先对排序后的字符串进行MD5
			byte[] dataBytes = DigestUtils.md5(data);
			//对sourcekey进行MD5,得到密钥
			SecretKey secretkey = new SecretKeySpec(DigestUtils.md5(secret), HMAC_ALGORITHM);
			mac.init(secretkey);
			//HmacSHA256加密
			doFinal = mac.doFinal(dataBytes);
		} catch (NoSuchAlgorithmException | InvalidKeyException e) {
			throw new RuntimeException(e);
		}
		String checksum = Hex.encodeHexString(doFinal).toLowerCase();
		return checksum;
	}
}

