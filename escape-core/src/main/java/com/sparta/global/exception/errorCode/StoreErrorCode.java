package com.sparta.global.exception.errorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum StoreErrorCode implements ErrorCode{
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "해당 매장을 찾을 수 없습니다."),
    INVALID_STORE_STATUS(HttpStatus.BAD_REQUEST.value(), "해당 매장은 접근할 수 없는 상태입니다."),
    USER_NOT_STORE_MANAGER(HttpStatus.BAD_REQUEST.value(), "매장의 매니저가 아니므로 접근할 수 없습니다.");

    private final int httpStatusCode;
    private final String errorDescription;
}