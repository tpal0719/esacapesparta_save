package com.sparta.global.exception.errorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {
    USER_NOT_AUTHORITY(HttpStatus.UNAUTHORIZED.value(),"접근 권한이 없습니다." ),
    USER_NO_ONE(HttpStatus.NOT_FOUND.value(),"유저가 존재하지 않습니다." ),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "해당 유저를 찾을 수 없습니다."),
    USER_DUPLICATION(HttpStatus.BAD_REQUEST.value(), "이미 등록된 이메일 입니다."),
    USER_WITHDRAW(HttpStatus.BAD_REQUEST.value(), "해당 사용자는 탈퇴한 사용자입니다.");

    private final int httpStatusCode;
    private final String errorDescription;

}