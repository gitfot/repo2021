package org.fun.test.order.controller;

import lombok.RequiredArgsConstructor;
import org.fun.test.order.entity.GetOutletAddressEntity;
import org.fun.test.order.entity.WareHouseEntity;
import org.fun.test.order.service.OrderService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author wanwan 2021/10/15
 */

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	@PostMapping("/getOutletAddressList")
	public Map getOutletAddressList(@RequestBody GetOutletAddressEntity baseInputModel) {
		return orderService.getOutletAddressList(baseInputModel);
	}

	/**
	 * 校验库存
	 */
	@PostMapping("/checkStorage")
	public Map checkStorage(@RequestBody WareHouseEntity baseInputModel) throws Exception {
		return orderService.getRetrieveMIAPProductList(baseInputModel);
	}
}
