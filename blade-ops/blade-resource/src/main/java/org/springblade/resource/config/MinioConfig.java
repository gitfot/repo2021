package org.springblade.resource.config;

import io.minio.MinioClient;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description 文件系统初始化
 * @Author zz
 * @Date 2021/5/2
 */
@Configuration
@Data
@AllArgsConstructor
@ConfigurationProperties(prefix = "minio")
@EnableConfigurationProperties
public class MinioConfig {

	/**
	 * logger
	 */
	private final static Logger logger = LoggerFactory.getLogger(MinioConfig.class);

	@Value("${minio.url}")
	private String url;

	@Value("${minio.access}")
	private String accessKey;

	@Value("${minio.secret}")
	private String secretKey;

	@Value("${minio.bucket}")
	private String bucketName;

	/**
	 * 外网地址
	 */
	private String extranetUrl;

	/**
	 * Minio文件系统配置
	 */
	@Bean(name = "minioClient")
	public MinioClient minioClient() throws Exception {
		logger.info("---------- Minio文件系统初始化加载 ----------");
		MinioClient minioClient = new MinioClient(url, accessKey, secretKey);
		// 判断Bucket是否存在
		boolean isExist = minioClient.bucketExists(bucketName);
		if(isExist) {
			logger.info("---------- Minio文件系统Bucket已存在 ----------");
		} else {
			// 不存在创建一个新的Bucket
			minioClient.makeBucket(bucketName);
			logger.info("---------- Minio文件系统Bucket已创建 ----------");
		}
		logger.info("---------- Minio文件系统初始化完成 ----------");
		return minioClient;
	}

}

