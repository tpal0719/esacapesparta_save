package com.sparta.global.exception.customException;

import com.sparta.global.exception.errorCode.ErrorCode;

public class EscapeRoomException  extends GlobalCustomException{
    public EscapeRoomException(ErrorCode errorCode) {
        super(errorCode);
    }
}