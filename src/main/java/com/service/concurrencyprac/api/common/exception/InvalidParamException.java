package com.service.concurrencyprac.api.common.exception;

import com.service.concurrencyprac.api.common.response.ErrorCode;

public class InvalidParamException extends BaseException {

    public InvalidParamException() {
        super(ErrorCode.COMMON_INVALID_PARAMETER);
    }

    public InvalidParamException(ErrorCode errorCode) {
        super(errorCode);
    }

    public InvalidParamException(String errorMessage) {
        super(errorMessage, ErrorCode.COMMON_INVALID_PARAMETER);
    }

    public InvalidParamException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
