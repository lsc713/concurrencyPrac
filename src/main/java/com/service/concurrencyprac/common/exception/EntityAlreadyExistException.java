package com.service.concurrencyprac.common.exception;

import com.service.concurrencyprac.common.response.ErrorCode;

public class EntityAlreadyExistException extends BaseException {

    public EntityAlreadyExistException() {
        super(ErrorCode.COMMON_INVALID_PARAMETER);
    }

    public EntityAlreadyExistException(String message) {
        super(message, ErrorCode.COMMON_INVALID_PARAMETER);
    }

}
