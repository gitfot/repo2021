package com.fun.excel.temp;

import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import lombok.Data;

/**
 * @author wanwan 2021/12/8
 */
@Data
@ContentRowHeight(15)
@ColumnWidth(16)
public class ChildParam {
	private String	parentName;
	private String	typeName;
	private String	source;
	private String	itemName;
	private String	itemDesc;
	private String	isKnow;
	private String	numberConfig;
	private String	classifyCode;
	private String	objectIndustryName;
}
