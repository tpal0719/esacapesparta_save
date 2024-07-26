package com.sparta.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class EditPasswordRequestDto {

    @NotBlank(message = "현재 비밀번호를 입력해주세요.")
    private String currentPassword;

    @NotBlank(message = "새 비밀번호를 입력해주세요.")
    private String newPassword;
}
