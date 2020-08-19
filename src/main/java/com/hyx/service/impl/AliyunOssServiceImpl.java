package com.hyx.service.impl;

import com.hyx.service.aliyunOssService;
import com.hyx.util.aliyun.OssPicture;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AliyunOssServiceImpl implements aliyunOssService {

    private OssPicture oss = new OssPicture();
    @Override
    public void putBucket(String bucketName) {
        oss.putBucket(bucketName);
    }

    @Override
    public void getObject(String bucketName, String objectName) {
        oss.getObject(bucketName,objectName);
    }

    @Override
    public void putfile(String bucketName, String objectName, MultipartFile path) {
          oss.putfile(bucketName,objectName,path);
    }
}
