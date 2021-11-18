package com.fun.ds.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fun.common.utils.api.R;
import com.fun.ds.entity.Sales;
import com.fun.ds.mapper.StockMapper;
import com.fun.ds.service.SalesService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * (sales)表控制层
 *
 * @author zz
 * @since 2021-10-18 18:01:18
 */
@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor
@Api(value = "xxx", tags = "xxx接口")
public class SalesController {

	private final SalesService salesService;
	private final StockMapper stockMapper;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入sales")
	public R<?> detail(Sales sales) {
		QueryWrapper<Sales> salesQueryWrapper = new QueryWrapper<>(sales);
		Sales detail = salesService.getOne(salesQueryWrapper);
		return R.data(detail);
	}

	/**
	 * 详情
	 */
	@GetMapping("/test")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入sales")
	public R<?> test() {

//		return R.data(salesService.list());
		return R.data(stockMapper.getAllOutLetAddress(null));
	}

	/**
	 * 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入sales")
	public R<?> save(@Valid @RequestBody Sales sales) {
		return R.status(salesService.save(sales));
	}
}
