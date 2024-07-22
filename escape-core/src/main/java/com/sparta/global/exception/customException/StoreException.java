package com.sparta.global.exception.customException;

import com.sparta.global.exception.errorCode.ErrorCode;

public class StoreException  extends GlobalCustomException{
    public StoreException(ErrorCode errorCode) {
        super(errorCode);
    }
}