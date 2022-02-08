package org.fun.test.spider.entity;

import lombok.Data;

import java.util.List;

/**
 * @author wanwan 2021/12/28
 */
@Data
public class QcTask {

	List<String> adminOrganization;
	String columnSort;
	String filter;
	String page;
	String status;
	String taskName;
	Integer taskSheetType;
}
