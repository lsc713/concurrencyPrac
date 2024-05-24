package com.service.concurrencyprac.common.util;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Objects;

public class HttpRequestUtils {
/*
* 브라우저나 장치정보를 수집하여 기능을 활성화하거나 디버깅정보를 제공하는데 유용한 메서드
* */
    public static String getUserAgent(HttpServletRequest request) {
        return Objects
            .nonNull(request.getHeader("user-agent")) ? request.getHeader("user-agent") : "";
    }
/*클라이언트의 IP주소를 식별하여 보안로그를 기록하거나 구현하는데 사용*/
    public static String getRemoteAddr(HttpServletRequest request) {
        return Objects.nonNull(request.getHeader("X-FORWARDED-FOR")) ? request.getHeader(
            "X-FORWARDED-FOR") : request.getRemoteAddr();
    }

}
