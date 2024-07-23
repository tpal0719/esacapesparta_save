package com.sparta.domain.escapeRoom.entity;

import com.sparta.domain.store.entity.Store;
import com.sparta.global.exception.customException.EscapeRoomException;
import com.sparta.global.exception.customException.StoreException;
import com.sparta.global.exception.errorCode.EscapeRoomErrorCode;
import com.sparta.global.exception.errorCode.StoreErrorCode;

public enum EscapeRoomStatus {
    ACTIVE,
    DEACTIVE;

    public static void verifyStoreIsActive(EscapeRoom escapeRoom) {
        if(!escapeRoom.getEscapeRoomStatus().equals(ACTIVE)) {
            throw new EscapeRoomException(EscapeRoomErrorCode.INVALID_ESCAPE_ROOM_STATUS);
        }
    }
}
