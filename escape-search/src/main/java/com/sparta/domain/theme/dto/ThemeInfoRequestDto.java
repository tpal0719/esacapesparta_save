package com.sparta.domain.theme.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ThemeInfoRequestDto {
    @NotNull(message = "방탈출 카페 id가 없습니다.")
    private Long storeId;
}
