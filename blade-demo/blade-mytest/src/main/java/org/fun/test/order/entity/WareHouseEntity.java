package org.fun.test.order.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wanwan 2021/10/15
 */
@Data
@ApiModel("WareHouse: 库存查询")
public class WareHouseEntity {
	@ApiModelProperty(value = "仓库编码（多门店时英文逗号隔开，查询全量为ALL）")
	private String warehouseCode;
	@ApiModelProperty(value = "产品编码")
	private String productCode;
	@ApiModelProperty(value = "查询类型1：单门店查询2：多门店查询(PT,为1，ALL为2)")
	private String queryType;
	@ApiModelProperty(value = "购买数量")
	private Integer productQuantity;
}
