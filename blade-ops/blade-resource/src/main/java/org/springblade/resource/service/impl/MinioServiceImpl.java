package org.springblade.resource.service.impl;

import org.springblade.resource.config.MinioConfig;
import org.springblade.resource.service.MinioService;
import org.springblade.resource.utils.MinioUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

/**
 * Minio接口封装实现
 * @author hardy.ma
 */
@Service
public class MinioServiceImpl implements MinioService {

	@Autowired
	private MinioConfig minioConfig;

    @Autowired
    private MinioUtil minioUtil;

    /**
     * 判断 bucket是否存在
     *
     * @param bucketName
     * @return
     */
    @Override
    public boolean bucketExists(String bucketName) {
        return minioUtil.bucketExists(bucketName);
    }

    /**
     * 创建 bucket
     *
     * @param bucketName
     */
    @Override
    public void makeBucket(String bucketName) {
        minioUtil.makeBucket(bucketName);
    }

    /**
     * 文件上传
     *
     * @param bucketName
     * @param objectName
     * @param filename
     */
    @Override
    public String putObject(String bucketName, String objectName, String filename) {
        minioUtil.putObject(bucketName, objectName, filename);
		return minioUtil.getObjectUrl(bucketName, objectName);
    }


    @Override
    public String putObject(String bucketName, String objectName, InputStream stream) {
        minioUtil.putObject(bucketName, objectName, stream);
		return minioUtil.getObjectUrl(bucketName, objectName);
    }

    /**
     * 文件上传
     *
     * @param bucketName
     * @param multipartFile
     */
    @Override
    public String putObject(String bucketName, MultipartFile multipartFile, String filename) {
        minioUtil.putObject(bucketName, multipartFile, filename);
		return minioUtil.getObjectUrl(bucketName, filename);
    }

    /**
     * 删除文件
     * @param bucketName
     * @param objectName
     */
    @Override
    public boolean removeObject(String bucketName,String objectName) {
        return minioUtil.removeObject(bucketName,objectName);
    }

    /**
     * 下载文件
     *
     * @param fileName
     * @param originalName
     * @param response
     */
    @Override
    public void downloadFile(String bucketName, String fileName, String originalName, HttpServletResponse response) {
        minioUtil.downloadFile(bucketName,fileName, originalName, response);
    }

    /**
     * 获取文件外网路径
     * @param fileName
     * @return
     */
    @Override
    public String getExtranetUrl(String fileName) {
		return minioConfig.getExtranetUrl()+fileName;
    }

}
