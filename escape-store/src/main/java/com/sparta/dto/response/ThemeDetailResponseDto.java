package com.sparta.dto.response;

import com.sparta.domain.theme.entity.Theme;
import com.sparta.domain.theme.entity.ThemeStatus;
import lombok.Getter;

@Getter
public class ThemeDetailResponseDto {
    private Long storeId;
    private String title;
    private String contents;
    private Long level;
    private String duration;
    private String theme;
    private Long price;
    private ThemeStatus themeStatus;

    public ThemeDetailResponseDto(Theme theme) {
        this.storeId = theme.getStore().getId();
        this.title = theme.getTitle();
        this.contents = theme.getContents();
        this.level = theme.getLevel();
        this.duration = theme.getDuration();
        this.theme = theme.getThemeType().getName();
        this.price = theme.getPrice();
        this.themeStatus = theme.getThemeStatus();
    }
}
