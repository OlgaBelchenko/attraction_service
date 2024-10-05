package com.example.attraction_service.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Around(
            "execution(* com.example.attraction_service.repository.*.*(..)) " +
                    "|| execution(* com.example.attraction_service.service.*.*(..))")
    public Object logAroundRepositoryOrServiceMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        LOGGER.info("Entering method: {}", joinPoint.getSignature().getName());
        LOGGER.info("Exiting method: {}", joinPoint.getSignature().getName());
        return joinPoint.proceed();
    }
}
