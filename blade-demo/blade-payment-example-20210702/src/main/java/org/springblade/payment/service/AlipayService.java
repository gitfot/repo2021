package org.springblade.payment.service;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.payment.common.models.AlipayTradeRefundResponse;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import com.alipay.easysdk.payment.wap.models.AlipayTradeWapPayResponse;
import org.springblade.payment.utils.OrderUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author zz
 * @date 2021/7/27
 */

@Service
public class AlipayService {

	/**
	 * 支付成功后要跳转的页面
	 */
	@Value("${pay.alipay.returnUrl}")
	private String returnUrl;

	/**
	 * 支付宝前台手机网页支付中途取消跳转地址
	 */
	@Value("${pay.alipay.errorUrl}")
	private String errorUrl;

	public String page(String subject, String total) {

		try {
			AlipayTradePagePayResponse response = Factory.Payment
				.Page().pay(subject, OrderUtil.getOrderNo(), total, returnUrl);

			return response.body;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public String wap(String subject, String total) {
		try {
			AlipayTradeWapPayResponse response = Factory.Payment
				.Wap().pay(subject, OrderUtil.getOrderNo(), total, errorUrl, returnUrl);
			return response.body;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String refund(String outTradeNo, String refundAmount) {
		try {
			AlipayTradeRefundResponse response = Factory.Payment
				.Common()
				/** 调用交易退款(商家订单号, 退款金额) **/
				.refund(outTradeNo, refundAmount);

			if (response.getMsg().equals("Success")) {return "退款成功";}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "退款失败";
	}
}
