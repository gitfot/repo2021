package org.springblade.resource.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Description OSS工具类
 * @Author zz
 * @Date 2021/4/24
 */
@Component
public class OssUtil {

    //---------变量----------
    protected static final Logger log = LoggerFactory.getLogger(OssUtil.class);

    @Value("${aliyun.oss.endpoint}")
    private String endpoint;
    @Value("${aliyun.oss.accessKeyId}")
    private String accessKeyId;
    @Value("${aliyun.oss.accessKeySecret}")
    private String accessKeySecret;
    @Value("${aliyun.oss.bucketName}")
    private String bucketName;

    //存放在OSS上的路径
    private String filedir = "test/";

    /**
     * 1、单个文件上传
     * @param file
     * @return 返回完整URL地址
     */
    public String uploadFile(MultipartFile file) {
        String fileName = uploadImg2Oss(file);
        String str = getFileUrl(fileName);
        return str.trim();
    }

    /**
     * 1、单个文件上传(指定文件名（带后缀）)
     * @param file
     * @return 返回完整URL地址
     */
    public String uploadFile(MultipartFile file,String fileName) {
        try (InputStream inputStream = file.getInputStream()) {
            this.uploadFile2OSS(inputStream, fileName);
            return fileName;
        }
        catch (Exception e) {
            return "上传失败";
        }
    }

    /**
     * 2、多文件上传
     * @param fileList
     * @return 返回完整URL，逗号分隔
     */
    public String uploadFile(List<MultipartFile> fileList) {
        String fileUrl = "";
        String str = "";
        String photoUrl = "";
        for(int i = 0;i< fileList.size();i++){
            fileUrl = uploadImg2Oss(fileList.get(i));
            str = getFileUrl(fileUrl);
            if(i == 0){
                photoUrl = str;
            }else {
                photoUrl += "," + str;
            }
        }
        return photoUrl.trim();
    }

    /**
     * 通过文件名获取文件完整URL
     * @param fileName 文件名
     * @return URL
     */
    public String getFileUrl(String fileName) {
        if (fileName !=null && fileName.length()>0) {
            String[] split = fileName.split("/");
            String url = this.getUrl(this.filedir + split[split.length - 1]);
            return url;
        }
        return null;
    }

    //获取去掉参数的完整路径
    private String getShortUrl(String url) {
        String[] imgUrls = url.split("/?");
        return imgUrls[0].trim();
    }

    // 生成预签名URL（指定过期时长）
    private String getUrl(String key) {
        // 设置URL过期时间为20年 3600l* 1000*24*365*20
        Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 20);
        // 生成URL
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        URL url = ossClient.generatePresignedUrl(bucketName, key, expiration);
        if (url != null) {
            return getShortUrl(url.toString());
        }
        return null;
    }

    /**
     * 文件上传（使用UUID作为文件名）
     * @param file
     * @return 文件名
     */
    private String uploadImg2Oss(MultipartFile file) {
        //1、限制最大文件为20M
        if (file.getSize() > 1024 * 1024 *20) {
            return "图片太大";
        }

        //使用UUID字符串重新命名文件
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".")).toLowerCase(); //文件后缀
        String uuid = UUID.randomUUID().toString();
        String name = uuid + suffix;

        try {
            InputStream inputStream = file.getInputStream();
            this.uploadFile2OSS(inputStream, name);
            return name;
        }
        catch (Exception e) {
            return "上传失败";
        }
    }


    /**
     * 文件上传（指定文件名）
     * @param instream 输入流
     * @param fileName 文件名
     * @return
     */
    private String uploadFile2OSS(InputStream instream, String fileName) {
        String ret = "";
        try {
            //创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(instream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(getcontentType(fileName.substring(fileName.lastIndexOf("."))));
            objectMetadata.setContentDisposition("inline;filename=" + fileName);

            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//            OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
            PutObjectResult putResult = ossClient.putObject(bucketName, filedir + fileName, instream, objectMetadata);
            ret = putResult.getETag();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return ret;
    }

    /**
     * 获取上传的文件类型
     * @param FilenameExtension 文件后缀
     * @return
     */
    private static String getcontentType(String FilenameExtension) {
        if (FilenameExtension.equalsIgnoreCase(".bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equalsIgnoreCase(".gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equalsIgnoreCase(".jpeg") ||
                FilenameExtension.equalsIgnoreCase(".jpg") ||
                FilenameExtension.equalsIgnoreCase(".png")) {
            return "image/jpeg";
        }
        if (FilenameExtension.equalsIgnoreCase(".html")) {
            return "text/html";
        }
        if (FilenameExtension.equalsIgnoreCase(".txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equalsIgnoreCase(".vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equalsIgnoreCase(".pptx") ||
                FilenameExtension.equalsIgnoreCase(".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equalsIgnoreCase(".docx") ||
                FilenameExtension.equalsIgnoreCase(".doc")) {
            return "application/msword";
        }
        if (FilenameExtension.equalsIgnoreCase(".xml")) {
            return "text/xml";
        }
        //PDF
        if (FilenameExtension.equalsIgnoreCase(".pdf")) {
            return "application/pdf";
        }
        return "image/jpeg";
    }
}
