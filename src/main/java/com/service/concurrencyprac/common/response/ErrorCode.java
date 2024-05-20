package com.service.concurrencyprac.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    COMMON_INVALID_PARAMETER("요청 값이 올바르지 않습니다."),
    COMMON_ENTITY_NOT_FOUND("존재하지 않는 엔티티입니다.")
    ;

    private final String errorMessage;

    public String getErrorMessage(Object... args) {
        return String.format(errorMessage, args);
    }

}