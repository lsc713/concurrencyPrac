package com.service.concurrencyprac.common.exception;

import com.service.concurrencyprac.common.response.ErrorCode;

public class EntityNotFoundException extends BaseException {

    public EntityNotFoundException(){
        super(ErrorCode.COMMON_ENTITY_NOT_FOUND);
    }

    public EntityNotFoundException(String message) {
        super(message,ErrorCode.COMMON_ENTITY_NOT_FOUND);
    }

}
