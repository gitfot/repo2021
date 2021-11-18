package org.springblade.payment.controller;

import com.alipay.easysdk.factory.Factory;
import lombok.RequiredArgsConstructor;
import org.springblade.payment.service.AlipayService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zz
 * @date 2021/7/27
 */
@RestController
@RequestMapping("/alipay")
@RequiredArgsConstructor
public class AliPayController {

	private final AlipayService alipayService;

	/**
	 * 支付宝电脑网页支付
	 */
	@PostMapping("/page")
	public String page(String subject, String total) {
		subject = "ces";
		total = "10";
		return alipayService.page(subject, total);
	}

	/**
	 * 支付宝手机网页支付
	 */
	@PostMapping("/wap")
	public String wap(String subject, String total) {
		return alipayService.wap(subject, total);
	}

	/**
	 * 支付宝异步回调
	 */
	@PostMapping("/notify_url")
	public String notify_url(HttpServletRequest request) throws Exception {

		if (request.getParameter("trade_status").equals("TRADE_SUCCESS")) {
			System.out.println("=========支付宝异步回调========");

			Map<String, String> params = new HashMap<>();
			Map<String, String[]> requestParams = request.getParameterMap();
			for (String name : requestParams.keySet()) {
				params.put(name, request.getParameter(name));
			}

			/** 支付宝验签 **/
			if (Factory.Payment.Common().verifyNotify(params)) {
				/** 验签通过 **/
				System.out.println("交易名称: " + params.get("subject"));
				System.out.println("交易状态: " + params.get("trade_status"));
				System.out.println("支付宝交易凭证号: " + params.get("trade_no"));
				System.out.println("商户订单号: " + params.get("out_trade_no"));
				System.out.println("交易金额: " + params.get("total_amount"));
				System.out.println("买家在支付宝唯一id: " + params.get("buyer_id"));
				System.out.println("买家付款时间: " + params.get("gmt_payment"));
				System.out.println("买家付款金额: " + params.get("buyer_pay_amount"));
			}
		}
		return "success";
	}

	/**
	 * 支付宝退款
	 */
	@PostMapping("/refund")
	public String refund(String outTradeNo, String refundAmount) {
		return alipayService.refund(outTradeNo, refundAmount);
	}
}
