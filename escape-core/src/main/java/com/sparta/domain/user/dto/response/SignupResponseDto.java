package com.sparta.domain.user.dto.response;

import com.sparta.domain.user.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SignupResponseDto {

    private Long id;
    private String name;
    private String email;
    private Long point; // 일단 넣긴 했는데 필요한지 모르겠음
    private LocalDateTime createdAt;

    public SignupResponseDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.point = user.getPoint();
        this.createdAt = user.getCreatedAt();
    }

}
