package com.hyx.controller;

import com.hyx.dto.Result;
import com.hyx.pojo.User;
import com.hyx.service.RmiMannager;
import com.hyx.service.RmiUserService;
import com.hyx.service.impl.AliyunMailServiceImpl;
import com.hyx.service.impl.AliyunOssServiceImpl;
import com.hyx.service.impl.AliyunSmsServiceImpl;
import com.hyx.util.CookieUtil;
import com.hyx.util.DESUtil;
import com.hyx.util.Md5;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserController {
    @Resource
    private RmiMannager userService;
    private AliyunOssServiceImpl aliyunOss;
    private AliyunMailServiceImpl aliyunMail;
    private AliyunSmsServiceImpl aliyunSms;

    /**
     * 登录页面URL
     * @return
     */
    @RequestMapping("/loginpage")
    public String doLogin(){
        return "login";
    }

    /**
     * 登录页面
     * @param user  用户对象参数
     * @param map   用来返回用户对象
     * @param response
     * @param request
     * @return  登录成功重定向到登录成功页面，登录失败返回登录页面
     */
    @RequestMapping(value = "/login",produces = "application/json;charset=UTF-8")
    public String dologin(User user,Map<String,Object> map, HttpServletResponse response, HttpServletRequest request,String smsCode){
            String code = (String)request.getSession().getAttribute(user.getPhone());
            String pwd = user.getPwd();
            System.out.println("登录时的密码："+ pwd);
            String md5String = Md5.getMd5(pwd);
            System.out.println("Md5加密后的密码："+md5String);
            User user1 = userService.chooseUserService().findOne(user);

            //token生成并进行DES加密
            String token = DESUtil.desEncrypt(user1.getId()+","+ new Date().getTime(),"12345678");
            //放入cookie中返回
            CookieUtil.setCookie(response,"token",token,60*60*24);
            //判断加盐后密码是否一致和验证码是否一致
            if(user1.getPwd().equals(md5String)){
                map.put("user",user1);
                //判断手机号和验证码
                aliyunSms.getSms(user.getPhone(),code);
                return "success";
            }
            return "login";
    }

    /**
     * 注册页面的URL
     * @return 跳转到注册页面
     */
    @RequestMapping("/registpage")
    public String doregist(){
        return "regist";
    }

    /**
     * 注册页面
     * @param user 注册的用户对象
     * @return 跳转到登录页面
     */
    @RequestMapping("/regist")
    @ResponseBody
    public Result doregist(User user){
        String pwd = user.getPwd();
        System.out.println("注册时的密码："+ pwd);
        String md5String = Md5.getMd5(pwd);
        System.out.println("Md5加密后的密码："+md5String);
        user.setPwd(md5String);
        int num = userService.chooseUserService().insert(user);
        return Result.back(num);
    }

    /**
     * 发送短信
     * @param phone
     * @return
     */
    @RequestMapping("/sendSmsCode")
    @ResponseBody
    public String sendSms(String phone){
        return aliyunSms.setSms(phone);
    }

    /**
     * 邮件发送
     * @param email
     */
    @RequestMapping("/sendMail")
    public void sendMail(String email){
        aliyunMail.sendMail(email);
    }
    /**
     * 使用springmvc实现上传，采用MultipartFile的transfer()
     * @param uploadFile
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/upload", method = RequestMethod.POST)
    public String upFile (MultipartFile uploadFile, HttpSession session) throws Exception {
        //获取上传文件的名称
        String fileName = uploadFile.getOriginalFilename();
        //解决文件重名问题  重新拼装文件名
        String finalFileName = UUID.randomUUID().toString()+fileName.substring(fileName.lastIndexOf("."));
        //获取上传文件的路径
        String filePath = session.getServletContext().getRealPath("photo")+File.separator+finalFileName;
        aliyunOss.putfile("img",filePath,uploadFile);
        return "success";
    }

}
