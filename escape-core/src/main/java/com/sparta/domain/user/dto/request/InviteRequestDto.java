package com.sparta.domain.user.dto.request;

import com.sparta.domain.user.entity.UserType;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class InviteRequestDto {

    @NotBlank(message = "초대코드를 받을 이메일을 입력해주세요.")
    private String email;

    @NotBlank(message = "ADMIN,MANAGER중 한가지를 입력해주세요.")
    private UserType userType;
}
