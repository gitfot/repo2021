package org.springblade.resource.endpoint;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springblade.core.oss.model.BladeFile;
import org.springblade.core.tool.api.R;
import org.springblade.resource.config.MinioConfig;
import org.springblade.resource.service.MinioService;
import org.springblade.resource.utils.result.Result;
import org.springblade.resource.utils.result.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

/**
 * @author ：zz
 * @date ：Created in 2021/3/19 19:29
 * @description：MinIo OSS存储
 * @version:
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ossMinio/endpoint")
@Api(value = "minio对象存储端点", tags = "minio对象存储端点")
public class OssMinIoEndpoint {

	private MinioService minioService;

	@Autowired
	private MinioConfig minioConfig;

	@ApiOperation(value="使用minio文件上传")
	@PostMapping(value = "/uploadFile")
	@ApiImplicitParams({
		@ApiImplicitParam(dataType = "MultipartFile", name = "file", value = "上传的文件", required = true, paramType = "form"),
		@ApiImplicitParam(dataType = "String", name = "bucketName", value = "对象存储桶名称", required = false)
	})
	public R<BladeFile> uploadFile(MultipartFile file, String bucketName) {
		try {
			bucketName = StringUtils.isNotBlank(bucketName) ? bucketName : minioConfig.getBucketName();
			if (!minioService.bucketExists(bucketName)) {
				minioService.makeBucket(bucketName);
			}
			String fileName = file.getOriginalFilename();
			String objectName = DigestUtils.md5Hex(file.getBytes())+ fileName.substring(fileName.lastIndexOf("."));
			InputStream inputStream = file.getInputStream();
			String urlResult = minioService.putObject(bucketName, objectName, inputStream);
			inputStream.close();
			String name = StringUtils.replace(urlResult,minioConfig.getUrl(),"");
			BladeFile bladeFile = new BladeFile();
			bladeFile.setLink(minioService.getExtranetUrl(name));
			bladeFile.setName(name);
			bladeFile.setOriginalName(fileName);
			return R.data(bladeFile);
		} catch (Exception e) {
			e.printStackTrace();
			return R.fail("上传失败");
		}
	}

	/**
	 * 删除文件
	 * @param fileName
	 */
	@ApiOperation(value = "删除文件")
	@ApiImplicitParams({
		@ApiImplicitParam(dataType = "String", name = "fileName", value = "文件名称", required = true),
		@ApiImplicitParam(dataType = "String", name = "bucketName", value = "对象存储桶名称", required = false)
	})
	@PostMapping("/deleteFile")
	public Result deleteFile(String fileName, String bucketName) {
		try {
			bucketName = StringUtils.isNotBlank(bucketName) ? bucketName : minioConfig.getBucketName();
			minioService.removeObject(bucketName, fileName);
			return ResultUtil.sendSuccessMessage("删除成功");
		} catch (Exception e) {
			return ResultUtil.sendErrorMessage("上传失败");
		}
	}

	/**
	 *下载文件
	 * @param bucketName
	 * @param fileName
	 * @param originalName
	 * @param response
	 */
	@ApiOperation(value = "下载文件")
	@ApiImplicitParams({
		@ApiImplicitParam(dataType = "String", name = "bucketName", value = "对象存储桶名称", required = false),
		@ApiImplicitParam(dataType = "String", name = "fileName", value = "文件名称", required = true),
		@ApiImplicitParam(dataType = "String", name = "originalName", value = "原文件名称", required = true),
		@ApiImplicitParam(dataType = "HttpServletResponse", name = "response", value = "返回response", required = false)
	})
	@PostMapping("/downloadFile")
	public Result downloadFile(String bucketName, String fileName, String originalName, HttpServletResponse response){
		try {
			bucketName = StringUtils.isNotBlank(bucketName) ? bucketName : minioConfig.getBucketName();
			minioService.downloadFile(bucketName, fileName, originalName, response);
			return ResultUtil.sendSuccessMessage("下载成功");
		} catch (Exception e) {
			return ResultUtil.sendErrorMessage("下载失败");
		}
	}

	/**
	 * 获取文件外网地址
	 * @param fileName
	 * @return
	 */
	@RequestMapping(value = "/getExtranetUrl", method = RequestMethod.GET)
	@ApiOperation(value = "获取文件外网地址", notes = "获取文件外网地址")
	@ResponseBody
	public String getExtranetUrl(@RequestParam String fileName) {
		return minioService.getExtranetUrl(fileName);
	}


}
