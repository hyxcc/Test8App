package com.hyx.rmiService;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StartServer {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("rmiServer.xml");
        System.out.println("RMI服务端启动");
    }
   }
