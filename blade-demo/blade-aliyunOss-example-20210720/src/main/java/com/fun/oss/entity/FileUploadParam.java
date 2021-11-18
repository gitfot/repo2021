package com.fun.oss.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author zz
 * @date 2021/7/19
 */
@ApiModel(value = "文件上传参数")
@Data
public class FileUploadParam implements Serializable {

	@NotNull(message = "文件名称不能为空")
	@ApiModelProperty(value = "文件名称")
	private String fileName;

	@NotNull(message = "文件类型不能为空")
	@ApiModelProperty(value = "文件类型")
	private String fileType;

	/**
	 * 经过base64编码后的文件
	 */
	@NotNull(message = "文件内容不能为空")
	@ApiModelProperty(value = "文件内容")
	private String data;
}
