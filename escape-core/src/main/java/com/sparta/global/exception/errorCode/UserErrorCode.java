package com.sparta.global.exception.errorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "해당 유저를 찾을 수 없습니다."),
    USER_NOT_MANAGER(HttpStatus.BAD_REQUEST.value(), "매니저만 접근할 수 있습니다.");

    private final int httpStatusCode;
    private final String errorDescription;

}