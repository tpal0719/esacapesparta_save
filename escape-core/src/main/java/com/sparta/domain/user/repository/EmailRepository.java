package com.sparta.domain.user.repository;

import java.time.Duration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Slf4j
@Repository
@RequiredArgsConstructor
public class EmailRepository {
    private final StringRedisTemplate redisTemplate;
    static final int EMAIL_VERIFICATION_LIMIT_IN_SECONDS = 300;

    public void saveCertificationNumber(String email, String certificationNumber) {
        redisTemplate.opsForValue()
                .set(email, certificationNumber, Duration.ofSeconds(EMAIL_VERIFICATION_LIMIT_IN_SECONDS));
    }

    public String getCertificationNumber(String email) {
        String str = redisTemplate.opsForValue().get(email);
        log.error(str);
        return str;
    }

    public void removeCertificationNumber(String email) {
        redisTemplate.delete(email);
    }
}
