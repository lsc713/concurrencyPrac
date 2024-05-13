package com.service.concurrencyprac.api.common.exception;

import com.service.concurrencyprac.api.common.response.ErrorCode;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    private ErrorCode errorCode;

    public BaseException() {

    }

    public BaseException(ErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }

    public BaseException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public BaseException(String message, ErrorCode errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }


}
