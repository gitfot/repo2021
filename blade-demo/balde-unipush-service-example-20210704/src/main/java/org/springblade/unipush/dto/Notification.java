package org.springblade.unipush.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zz
 * @date 2021/7/6
 */
@ApiModel(value = "消息推送实体")
@Data
public class Notification implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "标题")
	private String title;

	@ApiModelProperty(value = "内容")
	private String content;

	@ApiModelProperty(value = "客户端别名或者cid")
	private String aliasOrCid;

	@ApiModelProperty(value = "跳转地址")
	private String clickUrl;

}
