package com.sparta.domain.theme.dto;

import com.sparta.domain.theme.entity.Theme;
import lombok.Getter;

@Getter
public class ThemeInfoResponseDto {
    private String title;
    private String contents;
    private Long level;
    private String duration;
    private String themeImage;
    private Long price;

    public ThemeInfoResponseDto(Theme theme){
        this.title = theme.getTitle();
    }
}
