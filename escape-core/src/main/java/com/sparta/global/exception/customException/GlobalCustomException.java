package com.sparta.global.exception.customException;

import com.sparta.global.exception.errorCode.ErrorCode;
import lombok.Getter;

@Getter
public class GlobalCustomException extends RuntimeException {
    private final ErrorCode errorCode;

    public GlobalCustomException(ErrorCode errorCode) {
        super(errorCode.getErrorDescription());
        this.errorCode = errorCode;
    }
}