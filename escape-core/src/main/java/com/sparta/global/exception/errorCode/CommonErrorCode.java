package com.sparta.global.exception.errorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다."),
    USER_FORBIDDEN(HttpStatus.FORBIDDEN.value(), "접근 권한이 없습니다.");

    private final int httpStatusCode;
    private final String errorDescription;
}
