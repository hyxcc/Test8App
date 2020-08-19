package com.hyx.service;

import org.springframework.web.multipart.MultipartFile;

public interface aliyunOssService {
    void putBucket(String bucketName);
    void getObject(String bucketName,String objectName);
    void putfile(String bucketName,String objectName, MultipartFile multipartFile);
}
