package com.sparta.domain.escapeRoom.dto;

import com.sparta.domain.escapeRoom.entity.EscapeRoom;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EscapeRoomResponseDto {
    private String themeImage;
    private String title;

    public EscapeRoomResponseDto(EscapeRoom escapeRoom){
        this.themeImage = escapeRoom.getThemeImage();
        this.title = escapeRoom.getTitle();
    }
}
