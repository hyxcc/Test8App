package com.hyx.util.aliyun;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;
import com.aliyuncs.dm.model.v20151123.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SendMail {
    private String RegionId;
    private String AccountName;//我的发信地址
    private int AddressType;//地址类型。取值:0:为随机账号1:为发信地址
    private boolean ReplyToAddress;
    private String Subject;//邮件主题
    private String accessKeyId;
    private String accessKeySecret;
    private Logger logger = LoggerFactory.getLogger(SendMail.class);

    public boolean sendMail(String maile) {

        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou",accessKeyId,accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);

        SingleSendMailRequest request = new SingleSendMailRequest();
        request.setRegionId(RegionId);
        request.setAccountName(AccountName);
        request.setAddressType(AddressType);
        request.setReplyToAddress(ReplyToAddress);
        request.setToAddress(maile);
        request.setSubject(Subject);


        try {
            SingleSendMailResponse response = client.getAcsResponse(request);
            System.out.println(new Gson().toJson(response));
        } catch (ServerException e) {
            e.printStackTrace();
            logger.info(e.getMessage());
        } catch (ClientException e) {
            logger.info("ErrCode:" + e.getErrCode()+"ErrMsg:" + e.getErrMsg()+"RequestId:" + e.getRequestId());
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
            return false;
        }
        return true;
    }
    public String getRegionId() {
        return RegionId;
    }

    public void setRegionId(String regionId) {
        RegionId = regionId;
    }

    public String getAccountName() {
        return AccountName;
    }

    public void setAccountName(String accountName) {
        AccountName = accountName;
    }

    public int getAddressType() {
        return AddressType;
    }

    public void setAddressType(int addressType) {
        AddressType = addressType;
    }

    public boolean isReplyToAddress() {
        return ReplyToAddress;
    }

    public void setReplyToAddress(boolean replyToAddress) {
        ReplyToAddress = replyToAddress;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
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
