package org.springblade.payment.controller;

import com.fun.common.utils.result.Result;
import com.fun.common.utils.result.ResultUtil;
import com.github.binarywang.wxpay.bean.notify.SignatureHeader;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderV3Request;
import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryV3Result;
import com.github.binarywang.wxpay.bean.result.enums.TradeTypeEnum;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zz
 * @date 2021/7/26
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/pay/v3")
public class V3PayController {

	private final WxPayService wxService;

	/**
	 * 统一下单
	 * @param request 支付请求对象
	 * @param tradeType 支付类型
	 */
	@ApiOperation(value = "V3版 统一下单，并组装所需支付参数")
	@PostMapping("/createOrder/{tradeType:app|jsapi|native|h5}")
	public <T> T createOrderV3(@RequestBody WxPayUnifiedOrderV3Request request,
							   @PathVariable(value = "tradeType") String tradeType) throws WxPayException {
		switch (tradeType) {
			case "app":
				return this.wxService.createOrderV3(TradeTypeEnum.APP, request);
			case "jsapi":
				return this.wxService.createOrderV3(TradeTypeEnum.JSAPI, request);
			case "native":
				return this.wxService.createOrderV3(TradeTypeEnum.NATIVE, request);
			case "h5":
				return this.wxService.createOrderV3(TradeTypeEnum.H5, request);
			default:
				return null;
		}
	}

	/**
	 * 查询订单
	 * @param transactionId 微信支付订单号
	 * @param outTradeNo 商户订单号
	 */
	@ApiOperation(value = "查询订单")
	@GetMapping("/queryOrder")
	public WxPayOrderQueryV3Result queryOrder(@RequestParam(required = false) String transactionId,
											  @RequestParam(required = false) String outTradeNo)
		throws WxPayException {
		return this.wxService.queryOrderV3(transactionId, outTradeNo);
	}

	/**
	 * 关闭订单
	 * @param outTradeNo 商户订单号
	 */
	@ApiOperation(value = "关闭订单")
	@GetMapping("/closeOrder/{outTradeNo}")
	public Result<?> closeOrder(@PathVariable String outTradeNo) {
		try {
			this.wxService.closeOrderV3(outTradeNo);
//			return R.success("关闭订单成功");
			return ResultUtil.sendSuccessMessage("关闭订单成功");
		} catch (WxPayException e) {
			log.error("关闭订单失败-{}", e.getMessage());
//			return R.fail("关闭订单失败");
			return ResultUtil.sendErrorMessage("关闭订单失败");
		}
	}

	@ApiOperation(value = "支付回调通知处理")
	@PostMapping("/notify/order")
	public Result<?> parseOrderNotifyResult(@RequestBody String data, HttpServletRequest request) throws WxPayException {
		//设置签名
		SignatureHeader header = new SignatureHeader();
		header.setNonce(request.getHeader("Wechatpay-Nonce"));
		header.setSerial(request.getHeader("Wechatpay-Serial"));
		header.setTimeStamp(request.getHeader("Wechatpay-Timestamp"));
		header.setSignature(request.getHeader("Wechatpay-Signature"));
		//解析回调数据
		wxService.parseOrderNotifyV3Result(data, header);
		return ResultUtil.sendSuccessMessage("成功");
	}

	@ApiOperation(value = "获取微信沙箱的验签秘钥")
	@GetMapping("/getSignKey")
	public Result<?> getSignKey() throws WxPayException {
		String signKey = wxService.getSandboxSignKey();
		return ResultUtil.success(signKey);
	}
}
