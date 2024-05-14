package com.service.concurrencyprac.common.response;

import com.google.common.collect.Lists;
import com.service.concurrencyprac.common.exception.BaseException;
import com.service.concurrencyprac.common.interceptor.CommonHttpRequestInterceptor;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class CommonControllerAdvice {

    private static final List<ErrorCode> SPECIFIC_ALTER_TARGET_ERROR_CODE_LIST = Lists.newArrayList();

    /*http status : 200, Result = Fail
     * 비지니스 로직 처리상의 오류*/
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = BaseException.class)
    public CommonResponse onBaseException(BaseException e) {
        String eventId = MDC.get(CommonHttpRequestInterceptor.HEADER_REQUEST_UUID_KEY);
        if (SPECIFIC_ALTER_TARGET_ERROR_CODE_LIST.contains(e.getErrorCode())) {
            log.error("[BaseException] eventId = {}, cause = {}, errorMessage = {}", eventId,
                NestedExceptionUtils.getMostSpecificCause(e),
                NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        } else {
            log.warn("[BaseException] eventId = {}, cause = {}, errorMessage = {}", eventId,
                NestedExceptionUtils.getMostSpecificCause(e),
                NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        }
        return CommonResponse.fail(e.getMessage(), e.getErrorCode().name());
    }
}
