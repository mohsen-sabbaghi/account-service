package com.example.accountservice.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author m-sabbaghi
 * <a href="https://www.linkedin.com/in/sabbaghi/">...</a>
 * @version 6/14/2022
 */
@Aspect
@Component
@Order(1)
@Slf4j
public class DuplicationInterceptor {

    //it Must be static in order to retain the thread safety
    private static final Map<String, String> idempotenceCache = new ConcurrentHashMap<>();

    private final HttpServletRequest httpServletRequest;

    public DuplicationInterceptor(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    public static void clearCache() {
        idempotenceCache.clear();
    }

    public static int getcacheSize() {
        return idempotenceCache.size();
    }

    @Around(value = "@annotation(com.example.accountservice.interceptor.PreventDuplication)")
    public Object duplicationHandler(ProceedingJoinPoint pjp) throws Throwable {
        //get the annotation parameter name
        String annotationParamName = ((MethodSignature) pjp.getSignature()).getMethod().getAnnotation(PreventDuplication.class).parameterName();

        String trackId = httpServletRequest.getHeader(annotationParamName);

        if (trackId != null) {
            Map pathVariables = (Map) httpServletRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            String customerId = String.valueOf(pathVariables.get("customer-id"));

            if ("".equalsIgnoreCase(trackId) || idempotenceCache.containsValue(trackId))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Duplicated Request, check your Track-Id at header params");
            idempotenceCache.put(customerId, trackId);
        }
        return pjp.proceed();
    }
}
