package com.sparta.domain.user.dto.response;

import com.sparta.domain.user.entity.OAuthProvider;
import com.sparta.domain.user.entity.User;
import com.sparta.domain.user.entity.UserStatus;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private Long id;
    private String email;
    private String name;
    private Long point;
    private UserStatus userStatus;
    private OAuthProvider oAuthProvider;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.point = user.getPoint();
        this.userStatus = user.getUserStatus();
        this.oAuthProvider = user.getOAuthProvider();
    }
}
