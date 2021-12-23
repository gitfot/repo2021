package com.fun.excel.temp;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author wanwan 2021/12/9
 */
@Data
public class MetaData {

	private String title;

	private Map<String, List<ChildParam>> result;
}
