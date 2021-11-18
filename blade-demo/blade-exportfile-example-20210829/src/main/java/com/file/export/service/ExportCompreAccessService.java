package com.file.export.service;

import java.util.Map;

/**
 * @author zz
 * @date 2021/9/15
 */
public interface ExportCompreAccessService {

	Map<String, Object> getDataMap ();

	/**
	 * 获取初步评审数据
	 */
	void exportInitial(Long assessId, Long projectId);

	/**
	 * 获取详细评审汇总表数据
	 */
	void exportDetail(Long assessId, Long projectId);

	/**
	 * 获取价格评审汇总表数据
	 */
	void exportPrice(Long assessId, Long projectId);
}
