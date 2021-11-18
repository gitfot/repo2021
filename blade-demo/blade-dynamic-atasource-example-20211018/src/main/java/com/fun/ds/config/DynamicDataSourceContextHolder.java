package com.fun.ds.config;

import com.fun.ds.dsenum.DataSourceEnum;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wanwan 2021/10/19
 */
@Slf4j
public class DynamicDataSourceContextHolder {

	private static final ThreadLocal<DataSourceEnum> CONTEXT_HOLDER = new ThreadLocal<>();

	/**
	 * 设置数据库来源
	 */
	public static void setDateSourceType(DataSourceEnum dsType) {
		log.info("获取到的数据类型是{}", dsType);
		CONTEXT_HOLDER.remove();
		CONTEXT_HOLDER.set(dsType);
	}

	/**
	 * 获取数据库来源
	 */
	public static DataSourceEnum getDateSourceType() {
		return CONTEXT_HOLDER.get();
	}

	/**
	 * 清除数据库来源
	 */
	public static void clearDateSourceType() {
		CONTEXT_HOLDER.remove();
	}

}
