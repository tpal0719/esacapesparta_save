package com.sparta.domain.theme.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ThemeTimeRequestDto {
    @NotNull
    private Long storeId;
}
