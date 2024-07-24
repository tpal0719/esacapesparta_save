package com.sparta.domain.theme.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ThemeTimeResponseDto {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
