package com.sparta.domain.user.repository;

import com.sparta.global.exception.customException.EmailException;
import com.sparta.global.exception.errorCode.EmailErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

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
        if(str == null) {
//            log.error(str);
            throw new EmailException(EmailErrorCode.INVALID_VERIFICATION_CODE);
        }
        return str;
    }

    public void removeCertificationNumber(String email) {
        redisTemplate.delete(email);
    }
}
