package com.hyx.util.aliyun;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.exceptions.ServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SendSms {
    private Logger logger = LoggerFactory.getLogger(SendSms.class);
    //初始化ascClient,暂时不支持多region（请勿修改）
    private String accessKeyId;//你的accessKeyId
    private String accessKeySecret;//你的accessKeySecret，
    private String templateCode;
    private String signName;
    //初始化ascClient需要的几个参数
    private String product;//短信API产品名称（短信产品名固定，无需修改）
    private String domain;//短信API产品域名（接口地址固定，无需修改）

    public String getSms(String phone, String code){
       //设置超时时间-可自行调整
       System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
       System.setProperty("sun.net.client.defaultReadTimeout", "10000");

       IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
               accessKeySecret);
       try {
           DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
       } catch (ClientException e) {
           e.printStackTrace();
           logger.info(e.getMessage());
       }
       IAcsClient acsClient = new DefaultAcsClient(profile);

       //组装请求对象
       SendSmsRequest request = new SendSmsRequest();
       //使用post提交
       request.setMethod(MethodType.POST);
       //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为国际区号+号码，如“85200000000”
       request.setPhoneNumbers(phone);
       //必填:短信签名-可在短信控制台中找到
       request.setSignName(signName);
       //必填:短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
       request.setTemplateCode(templateCode);
       //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
       //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        //参考：request.setTemplateParam("{\"变量1\":\"值1\",\"变量2\":\"值2\",\"变量3\":\"值3\"}")
       request.setTemplateParam(code);
       //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
       //request.setSmsUpExtendCode("90997");

       //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
       //request.setOutId("yourOutId");

        //请求失败这里会抛ClientException异常
       SendSmsResponse sendSmsResponse = null;
       try {
           sendSmsResponse = acsClient.getAcsResponse(request);
       } catch (ClientException e) {
           e.printStackTrace();
           logger.info(e.getMessage());
       }
       if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
           System.out.println("短信发送成功");
           return "短信发送成功！";
            //请求成功
       }
            return "短信发送失败！";
   }

    /**
     *
     * @param phone 接收短信的手机号码。
     * @param time  短信发送日期，支持查询最近30天的记录。格式为yyyyMMdd，例如20181225。
     * @param PageSize 分页查看发送记录，指定每页显示的短信记录数量。取值范围为1~50。
     * @param CurrentPage 分页查看发送记录，指定发送记录的的当前页码。
     * @return 字符串
     */
    public String QuerySendDetails(String phone,String time,String PageSize,String CurrentPage) {
            DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,accessKeySecret);
            IAcsClient client = new DefaultAcsClient(profile);

            CommonRequest request = new CommonRequest();
            request.setSysMethod(MethodType.POST);
            request.setSysDomain("dysmsapi.aliyuncs.com");
            request.setSysVersion("2017-05-25");
            request.setSysAction("QuerySendDetails");
            request.putQueryParameter("RegionId", "cn-hangzhou");
            request.putQueryParameter("PhoneNumber",phone);
            request.putQueryParameter("SendDate",time);
            request.putQueryParameter("PageSize",PageSize);
            request.putQueryParameter("CurrentPage",CurrentPage);
            //发送回执ID，即发送流水号。调用发送接口SendSms或SendBatchSms发送短信时，返回值中的BizId字段。
            //request.putQueryParameter("BizId", "*****");
            try {
                CommonResponse response = client.getCommonResponse(request);
                return response.getData();
            } catch (ServerException e) {
                e.printStackTrace();
                logger.info(e.getMessage());
            } catch (ClientException e) {
                e.printStackTrace();
                logger.info(e.getMessage());
            }
            return "返回为空。";
    }
    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
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

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }
}
