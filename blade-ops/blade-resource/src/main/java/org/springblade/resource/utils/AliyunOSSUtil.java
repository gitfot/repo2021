package org.springblade.resource.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springblade.resource.config.AliyunOssConfig;
import org.springblade.resource.entity.FileUploadParam;
import org.springblade.resource.utils.result.Result;
import org.springblade.resource.utils.result.ResultUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author zz
 * @date 2021/7/19
 */
@Component
@RequiredArgsConstructor
public class AliyunOSSUtil {

	private final AliyunOssConfig ossConfig;
	private final OSSClient ossClient;

	/**
	 * 允许上传文件(图片)的格式
	 */
	private static final String[] IMAGE_TYPE = new String[]{".bmp", ".jpg",
		".jpeg", ".gif", ".png"};

	/**
	 * 图片最大尺寸(10M)
	 */
	public static final long FILE_MAX_SIZE = 1024 * 1024 * 10L;
	/**
	 * 过期时间（1年）
	 */
	public static final long FILE_EXPIRATION_TIME = 1000L * 3600 * 24 * 365;

	/*
	 * 图片上传
	 * 注：仅支持".bmp", ".jpg",".jpeg", ".gif", ".png"格式
	 */
	public Result<?> uploadImage(MultipartFile uploadFile) {

		// 校验图片格式
		boolean isLegal = false;
		for (String type : IMAGE_TYPE) {
			if (StringUtils.endsWithIgnoreCase(uploadFile.getOriginalFilename(), type)) {
				isLegal = true;
				break;
			}
		}
		if (!isLegal) {
			return ResultUtil.sendErrorMessage("不支持当前格式的图片");
		}
		// 获取文件原名称
		String originalFilename = uploadFile.getOriginalFilename();
		// 获取文件类型
		String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
		// 新文件名称
		String newFileName = UUID.randomUUID().toString() + fileType;
		// 构建日期路径, 例如：OSS目标文件夹/2020/10文件名
		String filePath = new SimpleDateFormat("yyyy/MM").format(new Date());
		// 文件上传的路径地址
		String uploadImageUrl = ossConfig.getBucketName() + "/" + filePath + "/" + newFileName;

		// 获取文件输入流
		InputStream inputStream = null;
		try {
			inputStream = uploadFile.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		/*
		 * 下面两行代码是重点坑：
		 * 现在阿里云OSS 默认图片上传ContentType是image/jpeg
		 * 也就是说，获取图片链接后，图片是下载链接，而并非在线浏览链接，
		 * 因此，这里在上传的时候要解决ContentType的问题，将其改为image/jpg
		 */
		ObjectMetadata meta = new ObjectMetadata();
		meta.setContentType("image/jpg");

		//文件上传至阿里云OSS
		ossClient.putObject(ossConfig.getBucketName(), uploadImageUrl, inputStream, meta);
		String returnImageUrl = "http://" + ossConfig.getBucketName() + "." + ossConfig.getEndpoint() + "/" + uploadImageUrl;

		return ResultUtil.success(returnImageUrl);
	}

	/**
	 * 上传文件至阿里云 OSS
	 * 文件上传成功,返回文件完整访问路径
	 */
	public Result<?> upload(MultipartFile file) {
		if (file == null) {
			return ResultUtil.parameterError("文件不能为空");
		}
		System.out.println(file.getSize());

		if (file.getSize() <= 0 || file.getSize() > FILE_MAX_SIZE) {
			return ResultUtil.parameterError("文件不能大于10M");
		}

		//文件名称
		String oldName = file.getOriginalFilename();
		//文件后缀
		String postfix = oldName.substring(oldName.lastIndexOf(".") + 1, oldName.length());
		//新文件名称
		String fileName = UUID.randomUUID().toString().toUpperCase()
			.replace("-", "")
			+ "." + postfix;
		//获取文件类型
		String fileType = file.getContentType();

		InputStream inputStream;
		try {
			inputStream = file.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
			return ResultUtil.sendErrorMessage(e.getMessage());
		}

		//上传文件
		return putFile(inputStream, fileType, fileName);
	}

	/**
	 * 上传文件
	 * 主要兼容客户端经过base64位编码的文件
	 */
	public Result<?> upload(FileUploadParam param) {

		if (param == null || StringUtils.isEmpty(param.getData())) {
			return ResultUtil.parameterError("文件不能为空");
		}
		//文件大小判断
		if (param.getData().length() <= 0 || param.getData().length() > FILE_MAX_SIZE) {
			return ResultUtil.parameterError("文件不能大于10M");
		}

		//老文件名
		String oldName = param.getFileName();
		//文件类型
		String fileType = param.getFileType();
		//新文件名
		String fileName = UUID.randomUUID().toString().toUpperCase()
			.replace("-", "")
			+ oldName.substring(oldName.lastIndexOf("."), oldName.length());

		InputStream inputStream;
		try {
			//将字符串转换为byte数组，这里的content是那一串base64密文
			byte[] bytes = new BASE64Decoder().decodeBuffer(param.getData());
			inputStream = new ByteArrayInputStream(bytes);
		} catch (IOException e) {
			e.printStackTrace();
			return ResultUtil.sendErrorMessage(e.getMessage());
		}

		//上传文件
		return putFile(inputStream, fileType, fileName);
	}

	/**
	 * 上传文件
	 */
	private Result<?> putFile(InputStream input, String fileType, String fileName) {
		Result<?> resoult;

		try {
			// 创建上传Object的Metadata
			ObjectMetadata meta = new ObjectMetadata();
			// 设置上传内容类型
			meta.setContentType(fileType);
			//被下载时网页的缓存行为
			meta.setCacheControl("no-cache");
			//创建上传请求
			PutObjectRequest request = new PutObjectRequest(ossConfig.getBucketName(), fileName, input, meta);
			//上传文件
			ossClient.putObject(request);

			//获取上传成功的文件地址
			resoult = new Result(200, getOssUrl(ossClient, fileName));

			System.out.println(resoult);
		} catch (OSSException | ClientException e) {
			e.printStackTrace();
			resoult = ResultUtil.sendErrorMessage(e.getMessage());
			return resoult;
		} finally {
			if (ossClient != null) {
				ossClient.shutdown();
			}
		}
		return resoult;
	}

	/**
	 * 根据文件名生成文件的访问地址
	 */
	private String getOssUrl(OSSClient ossClient, String key) {
		Date expiration = new Date(new Date().getTime() + FILE_EXPIRATION_TIME);// 生成URL
		GeneratePresignedUrlRequest generatePresignedUrlRequest;
		generatePresignedUrlRequest = new GeneratePresignedUrlRequest(ossConfig.getBucketName(), key);
		generatePresignedUrlRequest.setExpiration(expiration);
		URL url = ossClient.generatePresignedUrl(generatePresignedUrlRequest);
		return url.toString();
	}


	/**
	 * 通过文件名下载文件
	 *
	 * @param fileName      要下载的文件名
	 *                      例如：4DB049D0604047989183CB68D76E969D.jpg
	 * @param localFileName 本地要创建的文件名
	 *                      例如：C:\Users\Administrator\Desktop\test.jpg
	 */
	public void downloadFile(String fileName, String localFileName) {
		try {
			// 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
			ossClient.getObject(new GetObjectRequest(ossConfig.getBucketName(), fileName), new File(localFileName));
		} finally {
			if (ossClient != null) {
				ossClient.shutdown();
			}
		}
	}

	/**
	 * 列举 test 文件下所有的文件
	 */
	public List<String> listFile() {
		List<String> list = new ArrayList<>();
		try {
			// 构造ListObjectsRequest请求。
			ListObjectsRequest listObjectsRequest = new ListObjectsRequest(ossConfig.getBucketName());

			// 设置prefix参数来获取fun目录下的所有文件。
//            listObjectsRequest.setPrefix("test/");
			// 列出文件。
			ObjectListing listing = ossClient.listObjects(listObjectsRequest);
			// 遍历所有文件。
			for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
				//System.out.println(objectSummary.getKey());
				//把key全部转化成可以访问的url
				String url = getOssUrl(ossClient, objectSummary.getKey());
				list.add(url);
			}
		} finally {
			if (ossClient != null) {
				ossClient.shutdown();
			}
		}
		return list;
	}

	/**
	 * 批量文件删除(较慢)：适用于不同endPoint和BucketName
	 * @param fileUrls 需要删除的文件url集合
	 * @return int 成功删除的个数
	 */
	private int deleteFiles(List<String> fileUrls) {
		int count = 0;
		for (String url : fileUrls) {
			if (deleteFile(url)) {
				count++;
			}
		}
		return count;
	}

	/**
	 * 批量文件删除(较快)：适用于相同endPoint和BucketName
	 * @param fileUrls 需要删除的文件url集合
	 * @return int 成功删除的个数
	 */
	private int deleteFile(List<String> fileUrls) {
		int deleteCount = 0;    //成功删除的个数
		try {
			//根据url获取fileName
			List<String> fileNames = getFileName(fileUrls);
			if (ossConfig.getBucketName() == null || fileNames.size() <= 0) return 0;

			DeleteObjectsRequest request = new DeleteObjectsRequest(ossConfig.getBucketName()).withKeys(fileNames);
			DeleteObjectsResult result = ossClient.deleteObjects(request);
			deleteCount = result.getDeletedObjects().size();
		} catch (OSSException oe) {
			oe.printStackTrace();
			throw new RuntimeException("OSS服务异常:", oe);
		} catch (ClientException ce) {
			ce.printStackTrace();
			throw new RuntimeException("OSS客户端异常:", ce);
		} finally {
			if (ossClient != null) {
				ossClient.shutdown();
			}
		}
		return deleteCount;

	}

	/**
	 * 单文件删除
	 * @param fileUrl 需要删除的文件url
	 * @return boolean 是否删除成功
	 * 例如：http://*.oss-cn-beijing.aliyuncs.com/4DB049D0604047989183CB68D76E969D.jpg
	 */
	public boolean deleteFile(String fileUrl) {
		try {
			//根据url获取fileName
			String fileName = getFileName(fileUrl);
			if (ossConfig.getBucketName() == null || fileName == null) return false;
			GenericRequest request = new DeleteObjectsRequest(ossConfig.getBucketName()).withKey(fileName);
			ossClient.deleteObject(request);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (ossClient != null) {
				ossClient.shutdown();
			}
		}
		return true;
	}

	/**
	 * 根据url获取fileName
	 * @param fileUrl 文件url
	 * @return String fileName
	 */
	private static String getFileName(String fileUrl) {
		String str = "aliyuncs.com/";
		int beginIndex = fileUrl.indexOf(str);
		if (beginIndex == -1)
			return null;
		return fileUrl.substring(beginIndex + str.length());
	}

	/**
	 * 根据url获取fileNames集合
	 * @param fileUrls 文件url
	 * @return List<String>  fileName集合
	 */
	private List<String> getFileName(List<String> fileUrls) {
		List<String> names = new ArrayList<>();
		for (String url : fileUrls) {
			names.add(getFileName(url));
		}
		return names;
	}


	/**
	 * 获取文件类型
	 */
	private String contentType(String fileType) {
		fileType = fileType.toLowerCase();
		String contentType;
		switch (fileType) {
			case "bmp":
				contentType = "image/bmp";
				break;
			case "gif":
				contentType = "image/gif";
				break;
			case "png":
			case "jpeg":
			case "jpg":
				contentType = "image/jpeg";
				break;
			case "html":
				contentType = "text/html";
				break;
			case "txt":
				contentType = "text/plain";
				break;
			case "vsd":
				contentType = "application/vnd.visio";
				break;
			case "ppt":
			case "pptx":
				contentType = "application/vnd.ms-powerpoint";
				break;
			case "doc":
			case "docx":
				contentType = "application/msword";
				break;
			case "xml":
				contentType = "text/xml";
				break;
			case "mp4":
				contentType = "video/mp4";
				break;
			default:
				contentType = "application/octet-stream";
				break;
		}
		return contentType;
	}
}
