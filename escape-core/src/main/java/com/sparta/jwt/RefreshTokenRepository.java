package com.sparta.jwt;

import com.sparta.global.exception.customException.CustomSecurityException;
import com.sparta.global.exception.customException.UserException;
import com.sparta.global.exception.errorCode.SecurityErrorCode;
import com.sparta.global.exception.errorCode.UserErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByEmail(String email);
    Optional<RefreshToken> findByRefreshToken(String refreshToken);

    default RefreshToken findByEmailOrElseThrow(String email) {
        return findByEmail(email).orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));
    }

    default RefreshToken findByRefreshTokenOrElseThrow(String refreshToken) {
        return findByRefreshToken(refreshToken).orElseThrow(() -> new CustomSecurityException(SecurityErrorCode.REFRESH_TOKEN_MISMATCH));
    }
}