package com.sparta.dto.response;

import com.sparta.domain.theme.entity.ThemeTime;
import com.sparta.global.util.LocalDateTimeUtils;
import lombok.Getter;

@Getter
public class ThemeTimeDetailResponseDto {
    private Long themeTimeId;
    private String themeTitle;
    private String startTime;
    private String endTime;

    public ThemeTimeDetailResponseDto(ThemeTime themeTime) {
        this.themeTimeId = themeTime.getId();
        this.themeTitle = themeTime.getTheme().getTitle();
        this.startTime = LocalDateTimeUtils.parseLocalDateTimeToString(themeTime.getStartTime());
        this.endTime = LocalDateTimeUtils.parseLocalDateTimeToString(themeTime.getEndTime());
    }
}
