package com.sparta.domain.store.entity;

import com.sparta.global.exception.customException.StoreException;
import com.sparta.global.exception.errorCode.StoreErrorCode;

public enum StoreStatus {
    PENDING,
    ACTIVE,
    DEACTIVE;

    public static void verifyStoreIsActive(Store store) {
        if(!store.getStoreStatus().equals(ACTIVE)) {
            throw new StoreException(StoreErrorCode.INVALID_STORE_STATUS);
        }
    }
}
