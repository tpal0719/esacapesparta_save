package com.sparta.global.exception.customException;

import com.sparta.global.exception.errorCode.ErrorCode;

public class ReservationException extends GlobalCustomException {
    public ReservationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
