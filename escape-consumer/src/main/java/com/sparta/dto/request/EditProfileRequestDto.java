package com.sparta.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class EditProfileRequestDto {

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;
}
