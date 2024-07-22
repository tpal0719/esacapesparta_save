package com.sparta.global.exception.customException;

import com.sparta.global.exception.errorCode.ErrorCode;

public class RecommendationExcepiton extends GlobalCustomException {
    public RecommendationExcepiton(ErrorCode errorCode) {
        super(errorCode);
    }
}
