package com.fun.ds.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * (sales)表实体类
 *
 * @author zz
 * @since 2021-10-18 18:01:20
 */
@Data
@TableName("sales")
@EqualsAndHashCode
@ApiModel(value = "Sales对象", description = "对象")
public class Sales {

	private static final Long serialVersionId = 1L;

	@ApiModelProperty(value = "年份")
	private Integer year;

	@ApiModelProperty(value = "季节")
	private Object season;

	@ApiModelProperty(value = "销售额")
	private Integer sale;
}
