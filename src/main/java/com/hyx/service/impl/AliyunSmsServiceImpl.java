package com.hyx.service.impl;

import com.hyx.service.aliyunSmsService;
import com.hyx.util.JedisUtil;
import com.hyx.util.aliyun.SendSms;
import com.hyx.util.code.GetCode;
import org.codehaus.plexus.logging.LoggerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;

@Service
public class AliyunSmsServiceImpl implements aliyunSmsService {
    private JedisUtil.Keys jedisUtilKeys;
    private JedisUtil.Strings jedisUtilString;
    private String key;
    private HttpServletRequest request;
    private SendSms sms = new SendSms();
    private Logger logger = LoggerFactory.getLogger(AliyunSmsServiceImpl.class);

    /**
     * 发送短信，并将验证码存入缓存中，失效时间为5分钟
     * @param phone
     * @return
     */
    @Override
    public String setSms(String phone) {
        JedisUtil jedisUtil = new JedisUtil();
        String code;
        //redis中有无存放的当前手机号的验证码
        if (!jedisUtilKeys.exists(phone)){
            key = phone;
            code = GetCode.getCode();
            sms.getSms(phone,code);
            jedisUtilString.set(key,code);
            jedisUtil.expire(key,60);
            return "已成功发送";
        }
            code =jedisUtilString.get(key);
            sms.getSms(phone,code);
        return "重复获取";
    }

    /**
     * 查询缓存的验证码和用户传递的是否一致
     * @param phone
     * @param code
     * @return
     */
    @Override
    public String getSms(String phone, String code) {
        String finalCode = jedisUtilString.get(phone);
        if(finalCode.equals(code)){
            logger.info("验证码有误，输入验证码为"+ code+"生成验证码为"+finalCode);
            return "验证码正确！";
        }
        return "输入的验证码有误！";
    }

    /**
     * 查询发送的短信
     * @param phone
     * @param time
     * @param PageSize
     * @param CurrentPage
     * @return
     */
    @Override
    public String querySendDetails(String phone, String time, String PageSize, String CurrentPage) {
        return sms.QuerySendDetails(phone, time, PageSize, CurrentPage);
    }
}
