package com.service.concurrencyprac.common.logging;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class LogUtils {

    /*이 처럼 유틸클래스를 통해 로그기록을 남길 수 있고 ThreadLocal은 remove를 통한 삭제되어야 메머리누수 방지 가능.*/
    private static final ThreadLocal<LogInfo> LOG_INFO = new ThreadLocal<>();

    public static void setLogInfo(String uuid, String method, String uri) {
        LOG_INFO.set(new LogInfo(uuid, method, uri));
    }

    public static String getUUID() {
        return LOG_INFO.get().getUuid();
    }

    public static String getMethod() {
        return LOG_INFO.get().getMethod();
    }

    public static String getUri() {
        return LOG_INFO.get().getUri();
    }

    public static void removeLog() {
        LOG_INFO.remove();
    }

    @Getter
    @AllArgsConstructor
    public static class LogInfo {

        private String uuid;
        private String method;
        private String uri;
    }
}
