package com.hyx.service.impl;

import com.hyx.service.aliyunMailService;
import com.hyx.util.aliyun.SendMail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AliyunMailServiceImpl implements aliyunMailService {
    private Logger logger = LoggerFactory.getLogger(AliyunMailServiceImpl.class);

    @Override
    public String sendMail(String maile) {
        SendMail Mail = new SendMail();
        boolean flg = Mail.sendMail(maile);
        if(flg==true){
            logger.info("邮件发送成功");
            return "邮件发送成功";
        }
        logger.info("邮件发送失败");
        return "邮件发送失败";
    }
}
