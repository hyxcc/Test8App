package com.hyx.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Random;


public class ChooseService {
    private  Logger logger = LoggerFactory.getLogger(ChooseService.class);
    public Object  chooseService(Object m1, Object m2){
        int choose = new Random().nextInt(2);
        if(0 == choose){
            try{
                logger.info("Choose Server1");
                return m1;
            }catch (Exception e1){
                try{
                    logger.info("Server1 Error,Choose Server2");
                    return m2;
                }catch (Exception e2){
                    logger.info("Server1 and Server2 Error");
                    throw  new RuntimeException("Rmi Link Error");
                }
            }
        }
        if(1 == choose){
            try{
                logger.info("Choose Server1");
                return m2;
            }catch (Exception e1){
                try{
                    logger.info("Server1 Error,Choose Server2");
                    return m1;
                }catch (Exception e2){
                    logger.info("Server1 and Server2 Error");
                    throw  new RuntimeException("Rmi Link Error");
                }
            }
        }
        throw new RuntimeException("unknown error");
    }
}
