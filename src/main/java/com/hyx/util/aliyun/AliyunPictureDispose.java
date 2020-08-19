package com.hyx.util.aliyun;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GetObjectRequest;

import java.io.File;

public class AliyunPictureDispose {
    private String accessKeyId;
    private String accessKeySecret;
    private String endpoint;
    public void simpleDispose(){
        String bucketName = "image";
        String objectName = "邓伦.jpg";
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 图片缩放。
        String style = "image/resize,m_fixed,w_100,h_100";
        GetObjectRequest request = new GetObjectRequest(bucketName, objectName);
        request.setProcess(style);
        ossClient.getObject(request, new File("example-resize.jpg"));
        // 图片裁剪。
        style = "image/crop,w_100,h_100,x_100,y_100,r_1";
        request = new GetObjectRequest(bucketName, objectName);
        request.setProcess(style);
        ossClient.getObject(request, new File("example-crop.jpg"));
        // 图片旋转。
        style = "image/rotate,90";
        request = new GetObjectRequest(bucketName, objectName);
        request.setProcess(style);
        ossClient.getObject(request, new File("example-rotate.jpg"));
        // 图片锐化。
        style = "image/sharpen,100";
        request = new GetObjectRequest(bucketName, objectName);
        request.setProcess(style);
        ossClient.getObject(request, new File("example-sharpen.jpg"));
        // 添加水印。
        style = "image/watermark,text_SGVsbG8g5Zu-54mH5pyN5YqhIQ";
        request = new GetObjectRequest(bucketName, objectName);
        request.setProcess(style);
        ossClient.getObject(request, new File("example-watermark.jpg"));
        // 图片格式转换。
        style = "image/format,png";
        request = new GetObjectRequest(bucketName, objectName);
        request.setProcess(style);
        ossClient.getObject(request, new File("example-format.png"));
        // 获取图片信息。
        style = "image/info";
        request = new GetObjectRequest(bucketName, objectName);
        request.setProcess(style);
        ossClient.getObject(request, new File("example-info.txt"));
        // 关闭OSSClient。
        ossClient.shutdown();
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

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
}
