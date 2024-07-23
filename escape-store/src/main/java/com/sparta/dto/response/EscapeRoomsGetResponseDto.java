package com.sparta.dto.response;

import com.sparta.domain.escapeRoom.entity.EscapeRoom;
import lombok.Getter;

import java.util.List;

@Getter
public class EscapeRoomsGetResponseDto {
    private int totalEscapeRoom;
    private List<EscapeRoomDetailResponseDto> escapeRoomDtoList;

    public EscapeRoomsGetResponseDto(List<EscapeRoom> escapeRoomList) {
        this.totalEscapeRoom = escapeRoomList.size();
        this.escapeRoomDtoList = escapeRoomList.stream().map(EscapeRoomDetailResponseDto::new).toList();
    }
}
