package com.sparta.global.exception.customException;

import com.sparta.global.exception.errorCode.ErrorCode;

public class RefreshTokenException extends GlobalCustomException {
    public RefreshTokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
