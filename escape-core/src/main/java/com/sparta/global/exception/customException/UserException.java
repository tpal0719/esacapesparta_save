package com.sparta.global.exception.customException;

import com.sparta.global.exception.errorCode.ErrorCode;
import lombok.Getter;

@Getter
public class UserException extends GlobalCustomException{
    public UserException(ErrorCode errorCode) {
        super(errorCode);
    }
}
