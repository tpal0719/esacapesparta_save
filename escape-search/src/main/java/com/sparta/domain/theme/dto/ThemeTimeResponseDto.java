package com.sparta.domain.theme.dto;

import com.sparta.domain.theme.entity.ThemeTime;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ThemeTimeResponseDto {
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public ThemeTimeResponseDto(ThemeTime themeTime){
        this.startTime = themeTime.getStartTime();
        this.endTime = themeTime.getEndTime();
    }
}
