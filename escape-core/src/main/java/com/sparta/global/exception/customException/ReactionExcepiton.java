package com.sparta.global.exception.customException;

import com.sparta.global.exception.errorCode.ErrorCode;

public class ReactionExcepiton extends GlobalCustomException {
    public ReactionExcepiton(ErrorCode errorCode) {
        super(errorCode);
    }
}