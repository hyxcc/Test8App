/**
 * 发送短信验证码
 */
function sendValidateCode(obj){
    var mobileReg = /^1[3|4|5|6|7|8|9][0-9]\d{8}$/
    var countdown = 60, timerTag;
    var mobile=$("#mobile").val().trim();
    if(mobile != ""){
        var mobileResult = mobileReg.test(mobile);
        if(mobileResult){
            $.ajax({
                url: "verifyCode",
                type: "post",
                dataType: "json",
                async:false,
                data: {mobile:mobile},
                beforeSend: function (){
                    $("#sendSms").attr("disabled","disabled");
                    $(obj).css("background-color","#999");
                },
                success: function (data) {
                    if(data.result=="success"){
                        alert("验证码发送成功，请注意查收！")
                        setTime(obj,countdown,timerTag);
                    }else{
                        countdown = -1;
                    }
                },
                complete: function () {
                    $("#sendSms").removeAttr("disabled");
                },
            });
        }else{
            alert("请输入正确的手机号！");
        }
    }else{
        alert("手机号不能为空，请重新输入");
    }
}
/**
 * 短信验证码计数
 */
function setTime(obj,countdown,timerTag) {
    if (countdown <= 0) {
        $(obj).removeAttr("disabled").css("background-color","rgb(24, 197, 133)");
        $("#sendSms").attr("onclick","sendValidateCode(this)");
//            $(obj).html("重新发送");
        $("#sendSms").val("点击重新获取验证码").removeAttr("disabled").removeClass(
            "disabled");
        countdown = 60;
        clearTimeout(timerTag);
    } else {
        $("#sendSms").attr("disabled", true);//点击了"发送"按钮后，点击失效。
        $("#sendSms").addClass("disabled");//置灰
//            $(obj).html("重新发送(" + countdown + ")");
        $("#sendSms").attr("value","" + countdown + "秒后重新获取验证码");
        countdown--;
        timerTag = setTimeout(function() {
            setTime(obj,countdown,timerTag);
        }, 1000);
    }
}