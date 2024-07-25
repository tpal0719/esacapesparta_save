package com.sparta.global.exception.errorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum RefreshTokenErrorCode implements ErrorCode{

    REFRESH_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND.value(),"해당 사용자의 리프레쉬 토큰이 존재하지 않습니다, ");


    private final int httpStatusCode;
    private final String errorDescription;
}
