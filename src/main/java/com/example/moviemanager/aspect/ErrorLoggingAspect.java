package com.example.moviemanager.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

// Aspekt 3 — loguje wyjątki rzucone przez metody serwisu
@Aspect
@Component
public class ErrorLoggingAspect {
    @AfterThrowing(
            pointcut = "execution(* com.example.moviemanager.application.MovieManagementService.*(..))",
            throwing = "exception")
    public void logError(JoinPoint joinPoint, Throwable exception) {
        System.out.println("[AOP] Błąd w " + joinPoint.getSignature().getName() + ": " + exception.getMessage());
    }
}
