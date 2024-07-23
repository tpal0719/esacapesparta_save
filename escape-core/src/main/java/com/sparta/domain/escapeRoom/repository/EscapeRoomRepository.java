package com.sparta.domain.escapeRoom.repository;

import com.sparta.domain.escapeRoom.entity.EscapeRoom;
import com.sparta.global.exception.customException.EscapeRoomException;
import com.sparta.global.exception.errorCode.EscapeRoomErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EscapeRoomRepository extends JpaRepository<EscapeRoom, Long> {
    List<EscapeRoom> findAllByStoreId(Long storeId);

    default EscapeRoom findByIdOrElseThrow(Long themeId) {
        return findById(themeId).orElseThrow(() ->
                new EscapeRoomException(EscapeRoomErrorCode.ESCAPE_ROOM_NOT_FOUND));
    }
}
