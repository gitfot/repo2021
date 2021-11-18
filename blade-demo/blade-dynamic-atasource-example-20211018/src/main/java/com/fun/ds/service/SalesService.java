package com.fun.ds.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.fun.ds.entity.Sales;

/**
 * (sales)表服务接口
 *
 * @author zz
 * @since 2021-10-18 18:01:21
 */
public interface SalesService extends IService<Sales> {

	/**
	 * 自定义分页
	 */
	//IPage<SalesVO> selectSalesPage(IPage<SalesVO> page, SalesVO sales);
}
