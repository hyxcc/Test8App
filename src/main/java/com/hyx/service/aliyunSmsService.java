package com.hyx.service;

public interface aliyunSmsService {
    String setSms(String phone);
    String getSms(String phone,String code);
    String querySendDetails(String phone,String time,String PageSize,String CurrentPage);
}
