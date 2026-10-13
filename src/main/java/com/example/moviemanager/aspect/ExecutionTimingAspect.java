package com.example.moviemanager.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

// Aspekt 2 — mierzy czas wykonania metod serwisu
@Aspect
@Component
public class ExecutionTimingAspect {
    @Around("execution(* com.example.moviemanager.application.MovieManagementService.*(..))")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startNanos = System.nanoTime();
        try {
            return joinPoint.proceed();
        } finally {
            long elapsedNanos = System.nanoTime() - startNanos;
            System.out.println("[AOP] " + joinPoint.getSignature().getName() + " trwało " + elapsedNanos + " ns");
        }
    }
}
