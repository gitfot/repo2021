package org.springblade.message.validate.sms;

import lombok.RequiredArgsConstructor;
import org.springblade.message.config.SmsProperties;
import org.springblade.message.validate.impl.AbstractValidateCodeProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import com.aliyun.dysmsapi20170525.models.*;
import com.aliyun.teaopenapi.models.*;

/**
 * 手机验证码处理器
 * @author zz
 * @date 2021/6/23 9:31
 **/
@Component
@RequiredArgsConstructor
public class SmsValidateCodeProcessor extends AbstractValidateCodeProcessor {

	private final SmsProperties smsProperties;

	@Override
	protected void send(ServletWebRequest request, String validateCode) {
		//发送阿里云短信
		try {
			com.aliyun.dysmsapi20170525.Client client = SmsValidateCodeProcessor.createClient(smsProperties.getAccessKeyId(), smsProperties.getAccessKeySecret());
			SendSmsRequest sendSmsRequest = new SendSmsRequest()
				//设置手机
				.setPhoneNumbers(request.getParameter("sms"))
				.setSignName(smsProperties.getSignName())
				.setTemplateCode(smsProperties.getTemplateCode())
				.setTemplateParam("{\"code\":\""+validateCode+"\"}");
			// 复制代码运行请自行打印 API 的返回值
			client.sendSms(sendSmsRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(request.getParameter("sms") +
			"手机验证码发送成功，验证码为：" + validateCode);
	}

	/**
	 * 使用AK&SK初始化账号Client
	 */
	public static com.aliyun.dysmsapi20170525.Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
		Config config = new Config()
			// 您的AccessKey ID
			.setAccessKeyId(accessKeyId)
			// 您的AccessKey Secret
			.setAccessKeySecret(accessKeySecret);
		// 访问的域名
		config.endpoint = "dysmsapi.aliyuncs.com";
		return new com.aliyun.dysmsapi20170525.Client(config);
	}

}
