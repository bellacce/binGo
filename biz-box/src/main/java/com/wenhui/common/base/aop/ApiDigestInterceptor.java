package com.wenhui.common.base.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ApiDigestInterceptor {
    private final static Logger API_DIGEST_LOGGER = LoggerFactory.getLogger("API_DIGEST_LOGGER");

    @Around("bean(*Controller) && execution(* com.wenhui.project.web..*.*(..))")
    public Object invoke(ProceedingJoinPoint invocation) throws Throwable {
        long start = System.currentTimeMillis();

        String className = invocation.getSignature().getDeclaringType().getSimpleName();
        String method = invocation.getSignature().getName();
        Object result;
        String success = "Y";

        try {
            result = invocation.proceed();
        } catch (Throwable e) {
            success = "N";
            throw e;
        } finally {
            long time = System.currentTimeMillis() - start;
            API_DIGEST_LOGGER.info("({}.{},{},{})", className, method, success, time);
        }
        return result;
    }
}
