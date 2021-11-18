package org.fun.test.order.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 门市地点节点
 * @author wanwan 2021/10/13
 */
@Data
public class OutletAddressNode implements Serializable {

	private static final long serialVersionUID = 1689282114110237501L;

	private String label;

	private String code;

	private List<OutletAddressNode> nodeList;

}
