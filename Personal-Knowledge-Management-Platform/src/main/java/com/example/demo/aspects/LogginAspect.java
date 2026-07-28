package com.example.demo.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogginAspect {

    @Before(" execution( * com.example.demo.controllers.*.*(..))")
    public void startLogging(JoinPoint joinPoint){

        System.out.println("process logged start ! "+joinPoint.getSignature().getName());
    }
    @After(value = " execution( * com.example.demo.controllers.*.*(..))" )
    public void afterLoggin(JoinPoint joinPoint){
        System.out.println("process logged completed ! "+joinPoint.getSignature().getName());
    }
}
