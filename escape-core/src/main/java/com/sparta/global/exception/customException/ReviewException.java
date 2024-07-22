package com.sparta.global.exception.customException;

import com.sparta.global.exception.errorCode.ErrorCode;

public class ReviewException extends GlobalCustomException{
    public ReviewException(ErrorCode errorCode) {
        super(errorCode);
    }
}
