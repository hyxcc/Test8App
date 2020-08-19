package com.hyx.util.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Component
@Aspect
public class Log {
    //用来记录请求进入的时间，防止多线程时出错，这里用了ThreadLocal
    ThreadLocal<Long> startTime = new ThreadLocal<>();
    //定义日志对象
    Logger logger = LoggerFactory.getLogger(Log.class);
    /**
     * 定义切入点，记录service下入参，返回值，耗时分布
     */
    @Pointcut(" execution(* com.hyx.service.impl..*(..))")
    private void service(){}
    /**
     * 定义切入点，controll下面的所有类的方法的耗时
     */
    @Pointcut("execution(* com.hyx.controller..*(..))")
    private void controller(){}

    @Around(value = "controller()")
    public Object log1(ProceedingJoinPoint joinPoint) throws Throwable{

        String className =joinPoint.getTarget().getClass().getName();
        String methodName =joinPoint.getSignature().getName();
            logger.info("开始执行{}的{}方法",className,methodName);
            Long start = System.currentTimeMillis();
            Object object = joinPoint.proceed();
            Long end = System.currentTimeMillis();
            logger.info(methodName+"执行时间"+(end-start));
        logger.info("结束执行"+className+"中"+methodName+"执行时间"+(end-start));
        return object;
    }
    /*
    Service
     */
    @Before("service()")
    public void doBefore(JoinPoint joinPoint){
        startTime.set(System.currentTimeMillis());
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        //打印当前请求路径
        logger.info("RequestMapping:[{}]",request.getRequestURI());
        //打印请求参数
        logger.info("RequestParam:{}",Arrays.toString(joinPoint.getArgs()));
    }

    /**
     *方法返回之前执行，打印才返回值以及方法消耗时间
     */
    @AfterReturning(returning = "response",pointcut = "service()")
    public void doAfterRuning(Object response){
        //打印返回信息
        logger.info("Response:[{}]",response);
        //打印Service中处理请求的耗时
        logger.info("Service中处理请求的耗时 : [{}]",System.currentTimeMillis()-startTime.get());
    }
}
