package com.example.attraction_service.aspect;

import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.test.context.ContextConfiguration;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {LoggingAspect.class})
class LoggingAspectTest {
    @Mock
    private ProceedingJoinPoint joinPoint;

    @Mock
    private Logger log;

    @InjectMocks
    private LoggingAspect loggingAspect;

    @Test
    @SneakyThrows
    void testLogAround_success() {
        Object object = mock(Object.class);
        Signature signature = mock(Signature.class);
        when(joinPoint.getTarget()).thenReturn(object);
        when(joinPoint.getTarget().getClass().getName()).thenReturn("com.example.attraction_service.service.impl.AttractionServiceImpl");
        when(joinPoint.getSignature()).thenReturn(signature);
        when(joinPoint.getSignature().getName()).thenReturn("AttractionServiceImpl");

        loggingAspect.logAround(joinPoint);

        verify(joinPoint, times(1)).proceed();
        verify(log, times(2)).info(anyString(), anyString(), anyString());
    }
}