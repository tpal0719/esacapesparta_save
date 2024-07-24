package com.sparta.global.response;

import com.sparta.global.exception.errorCode.ErrorCode;
import lombok.Getter;

@Getter
public class ResponseErrorMessage<T> extends ResponseMessage {

//    @Builder
//    public ResponseErrorMessage(int statusCode, String message, Object data) {
//        super(statusCode, message, data);
//    }

    public ResponseErrorMessage(ErrorCode errorCode) {
        super(errorCode.getHttpStatusCode(), errorCode.getErrorDescription(), null);
    }

    public ResponseErrorMessage(ErrorCode errorCode, T data) {
        super(errorCode.getHttpStatusCode(), errorCode.getErrorDescription(), data);
    }
}