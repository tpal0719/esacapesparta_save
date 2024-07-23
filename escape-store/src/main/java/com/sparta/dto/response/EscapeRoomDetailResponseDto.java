package com.sparta.dto.response;

import com.sparta.domain.escapeRoom.entity.EscapeRoom;
import com.sparta.domain.escapeRoom.entity.EscapeRoomStatus;
import lombok.Getter;

@Getter
public class EscapeRoomDetailResponseDto {
    private Long storeId;
    private String title;
    private String contents;
    private Long level;
    private String duration;
    private String theme;
    private Long price;
    private EscapeRoomStatus escapeRoomStatus;

    public EscapeRoomDetailResponseDto(EscapeRoom escapeRoom) {
        this.storeId = escapeRoom.getStore().getId();
        this.title = escapeRoom.getTitle();
        this.contents = escapeRoom.getContents();
        this.level = escapeRoom.getLevel();
        this.duration = escapeRoom.getDuration();
        this.theme = escapeRoom.getTheme();
        this.price = escapeRoom.getPrice();
        this.escapeRoomStatus = escapeRoom.getEscapeRoomStatus();
    }
}
