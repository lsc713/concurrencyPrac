package com.service.concurrencyprac.common.logging;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class CommonHttpRequestInterceptor implements HandlerInterceptor {

    public static final String HEADER_REQUEST_UUID_KEY = "x-request-id";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) {
        String requestEventId = request.getHeader(HEADER_REQUEST_UUID_KEY);
        if (StringUtils.isEmpty(requestEventId)) {
            requestEventId = UUID.randomUUID().toString();
        }

        MDC.put(HEADER_REQUEST_UUID_KEY, requestEventId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
        Object handler, Exception ex) throws Exception {
        MDC.remove(HEADER_REQUEST_UUID_KEY);
    }
}
