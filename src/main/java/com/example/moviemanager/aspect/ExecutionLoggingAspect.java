package com.example.moviemanager.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

// Aspekt 1 — loguje wejście do metod serwisu bez dopisywania logów do logiki biznesowej
@Aspect
@Component
public class ExecutionLoggingAspect {
    @Before("execution(* com.example.moviemanager.application.MovieManagementService.*(..))")
    public void logMethodCall(JoinPoint joinPoint) {
        System.out.println("[AOP] Start: " + joinPoint.getSignature().getName());
    }
}
