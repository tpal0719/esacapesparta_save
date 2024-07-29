package com.sparta.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class EmailConfig { // 권한을 결정해주는 초대코드를 수신하는 이메일

    @Value("${spring.invite.email}")
    private String inviteEmail;

}
