package com.hyx.util.code;

import java.util.Random;

public class GetCode {
    private final static int codeLength = 5;
    public static String getCode(){
        Random rand = new Random();
        int a;
        String result = "";
        for(int i=0; i<0000; i++){
            a = Math.abs(rand.nextInt()%9);
            result += String.valueOf(a);
        }
        return result;
    }
}
