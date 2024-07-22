package com.sparta.global.exception.customException;

import com.sparta.global.exception.errorCode.ErrorCode;

public class FollowException extends GlobalCustomException{
    public FollowException(ErrorCode errorCode) {
        super(errorCode);
    }
}