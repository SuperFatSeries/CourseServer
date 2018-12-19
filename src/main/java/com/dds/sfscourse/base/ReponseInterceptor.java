package com.dds.sfscourse.base;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Aspect
@Order(-99) // 控制多个Aspect的执行顺序，越小越先执行
@Component
public class ReponseInterceptor {
    private static final Logger log = LoggerFactory.getLogger(ReponseInterceptor.class);


    @Pointcut("execution(public * com.dds.sfscourse..*.*(..))")
    public void Pointcut() {
    }

    //前置通知
    @Before("Pointcut()")
    public void beforeMethod(JoinPoint joinPoint){
        log.info("调用了前置通知");

    }

    //@After: 后置通知
    @After("Pointcut()")
    public void afterMethod(JoinPoint joinPoint){
        log.info("调用了后置通知");
    }
    //@AfterRunning: 返回通知 rsult为返回内容
    @AfterReturning(value="Pointcut()",returning="result")
    public void afterReturningMethod(JoinPoint joinPoint,Object result){
        log.info("调用了返回通知");
    }
    //@AfterThrowing: 异常通知
    @AfterThrowing(value="Pointcut()",throwing="e")
    public void afterReturningMethod(JoinPoint joinPoint, Exception e){
        log.info("调用了异常通知");
    }

    //@Around：环绕通知
    @Around("Pointcut()")
    public Object Around(ProceedingJoinPoint pjp) throws Throwable {
        log.info("around执行方法之前");
        Object object = pjp.proceed();
        log.info("around执行方法之后--返回值：" +object);
        return object;
    }
}
