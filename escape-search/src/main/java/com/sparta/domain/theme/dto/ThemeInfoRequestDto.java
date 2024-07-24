package com.sparta.domain.theme.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ThemeInfoRequestDto {
    @NotNull
    private Long storeId;
}
