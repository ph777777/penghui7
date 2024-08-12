package com.example.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/*
1. execution(public * *(..)) # ——表示匹配所有public方法
2. execution(* set*(..)) # ——表示所有以“set”开头的方法
3. execution(* com.pro.service.UserService.*(..)) # ——表示匹配所有UserService接口的方法
4. execution(* com.pro.service.*.*(..)) # ——表示匹配service包下所有的方法
5. execution(* com.pro.service..*.*(..)) # ——表示匹配service包和它的子包下的方法

# () 参数说明
()匹配没有参数；  (..)代表任意多个参数；   (*)代表一个参数，但可以是任意型；    (*,String)代表第一个参数为任何值,第二个为String类型。*/
public class WebLogAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebLogAspect.class);

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    // 切入点
    @Pointcut("execution(* com.pro.controller..*.*(..))")
    public void pc() {}

    // 前置通知
    @Before("pc()")
    public void doBefore(JoinPoint joinPoint) {
        // 记录请求开始时间
        startTime.set(System.currentTimeMillis());

        // 接收请求, 记录请求内容
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        // 记录请求内容
        LOGGER.info("URL: " + request.getRequestURL().toString());
        LOGGER.info("HTTP_METHOD: " + request.getMethod());
        LOGGER.info("IP: " + request.getRemoteAddr());
        LOGGER.info("CLASS_METHOD: " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        LOGGER.info("Args: " + Arrays.toString(joinPoint.getArgs()));
    }

    // 返回通知
    @AfterReturning(returning = "result", pointcut = "pc()")
    public void doAfterReturning(Object result) {
        // 请求返回内容
        LOGGER.info("RESPONSE: " + result);
        LOGGER.info("SPEND TIME: " + (System.currentTimeMillis() - startTime.get()));

        // 用完之后移除, 避免内存泄漏
        startTime.remove();
    }

    // 异常通知
    @AfterThrowing(value = "pc()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        // 获取类名加方法名
        String name = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        // 记录异常信息
        LOGGER.info("Exception_Class_Method: {}, Exception_Message: {}", name, e.getMessage());
    }

}
