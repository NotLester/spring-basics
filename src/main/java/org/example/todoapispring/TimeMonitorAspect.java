package org.example.todoapispring;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeMonitorAspect {

    @Around("@annotation(TimeMonitor)")
    public Object logTime(ProceedingJoinPoint joinPoint) {
        long startTime = System.currentTimeMillis();

        try {
            joinPoint.proceed();
        } catch (Throwable e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            long end = System.currentTimeMillis();
            long totalExecutionTime = end - startTime;
            System.out.println("Total execution time: " + totalExecutionTime + "ms");
        }

        return null;
    }
}
