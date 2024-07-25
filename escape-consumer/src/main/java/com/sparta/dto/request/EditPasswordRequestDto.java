package com.sparta.dto.request;

import lombok.Getter;

@Getter
public class EditPasswordRequestDto {

    private String currentPassword;

    private String newPassword;
}
