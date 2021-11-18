package org.springblade.resource.endpoint;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.resource.entity.FileUploadParam;
import org.springblade.resource.utils.AliyunOSSUtil;
import org.springblade.resource.utils.result.Result;
import org.springblade.resource.utils.result.ResultUtil;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 对象存储端点
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping("/oss/endpoint")
@Api(value = "对象存储端点", tags = "对象存储端点")
public class AliOssEndpoint {

	private final AliyunOSSUtil ossUtil;

	@PostMapping("/uploadFile")
	@ApiOperation("上传MultipartFile类型的文件")
	public Result<?> uploadFile(MultipartFile file) {

		Result<?> result = ossUtil.upload(file);
		return ResultUtil.success(result);
	}

	@PostMapping("/uploadData")
	@ApiOperation("上传base64编码的文件")
	public Result<?> uploadData(@RequestBody @Validated FileUploadParam param) {

		Result<?> result = ossUtil.upload(param);
		return ResultUtil.success(result);
	}

	@PostMapping("/downloadFile")
	@ApiOperation("下载文件")
	public Result<?> downloadFile(String fileNmae, String localFileName) {

		ossUtil.downloadFile(fileNmae, localFileName);
		return ResultUtil.success();
	}

	@PostMapping("/deleteFile")
	@ApiOperation("删除文件")
	public Result<?> deleteFile(String fileUrl) {

		ossUtil.deleteFile(fileUrl);
		return ResultUtil.success();
	}

	@PostMapping("/listFile")
	@ApiOperation("获取当前系统下所有文件")
	public Result<?> listFile() {
		List<String> list = ossUtil.listFile();
		return ResultUtil.success(list);
	}
}
