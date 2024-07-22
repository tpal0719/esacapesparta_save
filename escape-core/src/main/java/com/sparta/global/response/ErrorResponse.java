package com.sparta.global.response;

import com.sparta.global.exception.errorCode.ErrorCode;
import lombok.Getter;

@Getter
public class ErrorResponse extends BasicResponse {
    public ErrorResponse(Boolean isSuccess, int statusCode, String message, Object data) {
        super(isSuccess, statusCode, message, data);
    }

    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(false, errorCode.getHttpStatusCode(), errorCode.getErrorDescription(), null);
    }

    public static <T> ErrorResponse of(ErrorCode errorCode, T data) {
        return new ErrorResponse(false, errorCode.getHttpStatusCode(), errorCode.getErrorDescription(), data);
    }
}