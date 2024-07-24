package com.sparta.domain.escapeRoom.dto;

import com.sparta.domain.escapeRoom.entity.Theme;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EscapeRoomResponseDto {
    private String themeImage;
    private String title;

    public EscapeRoomResponseDto(Theme theme){
        this.themeImage = theme.getThemeImage();
        this.title = theme.getTitle();
    }
}
