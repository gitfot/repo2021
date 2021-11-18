package org.springblade.getui.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Map;

/**
 * @author zz
 * @date 2021/7/16
 */
@Data
@EqualsAndHashCode
@ApiModel(value = "PushParam对象", description = "推送参数对象")
public class PushParam implements Serializable {

	private static final Long serialVersionId = 1L;

	@ApiModelProperty(value = "标题")
	private String title;

	@ApiModelProperty(value = "标题")
	private String content;

	@ApiModelProperty(value = "cid或者别名")
	private String cidOrAlias;

	@ApiModelProperty(value = "自定义数据")
	private Map<String, String> payload;
}
