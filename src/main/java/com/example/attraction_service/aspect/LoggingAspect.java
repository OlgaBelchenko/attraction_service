package com.example.attraction_service.aspect;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Аспект для логирования методов репозитория и сервиса.
 */

@Component
@Aspect
@RequiredArgsConstructor
public class LoggingAspect {

    private final Logger log;

    /**
     * Метод для декларации пойнткатов в аннотации.
     */
    @Pointcut("within(com.example.attraction_service.repository..*)" +
            " || within(com.example.attraction_service.service..*)")
    public void packagePointcut() {
    }

    /**
     * Метод для логирования.
     */
    @Around("packagePointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String[] classNameList = joinPoint.getTarget().getClass().getName().split("\\.");
        String className = classNameList[classNameList.length - 1];
        log.info("Начало метода: {}.{}", className, joinPoint.getSignature().getName());
        Object result = joinPoint.proceed();
        log.info("Завершение метода: {}.{}", className, joinPoint.getSignature().getName());
        return result;
    }
}
