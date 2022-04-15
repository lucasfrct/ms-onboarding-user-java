package com.environment.infrastructure.utils;

import javax.servlet.http.HttpServletRequest;
 
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
 
@Aspect
@Component
public class ResponseMiddlewareAspect {
 
    @Around("@annotation(ResponseMiddleware)")
    public Object response(ProceedingJoinPoint joinPoint) throws Throwable {
 
        System.out.println("Input :\n" + joinPoint.getArgs()[0]);
 
        HttpServletRequest servletRequest = (HttpServletRequest) joinPoint.getArgs()[1];
         
        System.out.println(servletRequest.getRemoteAddr());
 
        Object result = joinPoint.proceed();
 
        System.out.println(result);
 
        return result;
    }
 
}