package com.hyx.util.aliyun;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class OssPicture {
    private String accessKeyId;
    private String accessKeySecret;
    private String endpoint;// Endpoint以杭州为例，其它Region请按实际情况填写。
    private Logger logger = LoggerFactory.getLogger(OssPicture.class);
    /**
     * 创建存储空间
     * @param bucketName 存储空间名称
     */
    public void putBucket(String bucketName){
            // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            // 创建存储空间。
        ossClient.createBucket(bucketName);
            // 关闭OSSClient。
        ossClient.shutdown();
    }
    /**
     * 上传文件
     * @param bucketName
     * @param objectName
     * @param multipartFile
     * @return
     */
    public void putfile(String bucketName, String objectName, MultipartFile multipartFile){
        //Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 创建PutObjectRequest对象。
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, "<yourObjectName>", new File("<yourLocalFile>"));

        try {
            ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(multipartFile.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
            logger.info(e.getMessage());
        }
        // 关闭OSSClient。
        ossClient.shutdown();
    }
    public String getEndpoint() {
        return endpoint;
    }

    /**
     * @param bucketName  存储空间名称
     * @param objectName 从OSS下载文件时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
     */
    public void getObject(String bucketName,String objectName){
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 调用ossClient.getObject返回一个OSSObject实例，该实例包含文件内容及文件元信息。
        OSSObject ossObject = ossClient.getObject(bucketName, objectName);
        // 调用ossObject.getObjectContent获取文件输入流，可读取此输入流获取其内容。
        InputStream content = ossObject.getObjectContent();
        if (content != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(content));
            while (true) {
                String line = null;
                try {
                    line = reader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (line == null) break;
                System.out.println("\n" + line);
            }
            // 数据读取完成后，获取的流必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作。
            try {
                content.close();
            } catch (IOException e) {
                e.printStackTrace();
                logger.info(e.getMessage());
            }
        }
        // 关闭OSSClient。
        ossClient.shutdown();
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }
}
