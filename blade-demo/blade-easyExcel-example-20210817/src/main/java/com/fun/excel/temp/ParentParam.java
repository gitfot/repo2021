package com.fun.excel.temp;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author wanwan 2021/12/8
 */
@Data
public class ParentParam {

	private Map<String,List<ChildParam>> entity;
}
