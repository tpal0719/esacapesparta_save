package com.sparta.jwt;

import com.sparta.domain.user.entity.User;
import com.sparta.global.exception.customException.UserException;
import com.sparta.global.exception.errorCode.UserErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    default RefreshToken findByEmailOrElseThrow(String email) {
        return findByEmail(email).orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));
    }

    Optional<RefreshToken> findByEmail(String email);

}