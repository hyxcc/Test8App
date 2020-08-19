<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>登录页面</title>
<link rel="stylesheet" href="../css/login.css">
<script src="../js/sms.js"></script>
</head>
<body>
    <div>
        <form action="/user/login" method="post">
            <table>
                <tr>
                    <td class="td1">用户名</td>
                    <td><input type="text" class="input1" name="name"></td>
                </tr>
                <tr>
                <td class="td1">密码</td>
                <td><input type="password" class="input1" name="pwd"></td>
                </tr>
                <tr>
                    <td  class="td1">手机号：</td>
                    <td ><input id="smsCode" name="smsCode" type="text"/></td>
                    <td><input type="button" id="sendSms" class="btn btn-primary" value="获取短信验证码" onclick="sendValidateCode(this)" /></td>
                </tr>
                <tr>
                    <td class="td1">验证码：</td>
                    <td><input type="text" class="input1" name="code"></td>
                </tr>
                <tr>
                <td class="td1" colspan="2">
                    <input type="checkbox" name="remember" value="true" checked="checked"> 记住用户名</td>
                </tr>

                <tr>
                    <td colspan="2">
                        <div class="btn-red">
                            <input type="submit" value="登录" id="login-btn">
                        </div>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</body>
</html>