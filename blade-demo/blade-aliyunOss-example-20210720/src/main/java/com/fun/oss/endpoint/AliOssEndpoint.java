package com.fun.oss.endpoint;

import com.fun.oss.entity.FileUploadParam;
import com.fun.oss.utils.AliyunOSSUtil;
import com.fun.oss.utils.result.Result;
import com.fun.oss.utils.result.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

	/**
	 * 上传文件
	 * @param file 文件本件
	 * @param dir bucket中的目录
	 */
	@PostMapping("/uploadFile")
	@ApiOperation("上传MultipartFile类型的文件")
	public Result<?> uploadFile(MultipartFile file, String dir) {

		Result<?> result = ossUtil.upload(file, dir);
		return ResultUtil.success(result);
	}

	/**
	 * 上传图片
	 * @param file 文件本件
	 * @param dir bucket中的目录
	 */
	@PostMapping("/uploadImage")
	@ApiOperation("上传MultipartFile类型的文件")
	public Result<?> uploadImage(MultipartFile file, String dir) {

		Result<?> result = ossUtil.uploadImage(file, dir);
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
	public Result<?> downloadFile(String fileName, String localFileName) {

		ossUtil.downloadFile(fileName, localFileName);
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
