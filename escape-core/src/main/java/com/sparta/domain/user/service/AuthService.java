package com.sparta.domain.user.service;

import com.sparta.domain.user.entity.User;
import com.sparta.domain.user.repository.UserRepository;
import com.sparta.global.exception.customException.AuthException;
import com.sparta.global.exception.errorCode.AuthErrorCode;
import com.sparta.jwt.JwtProvider;
import com.sparta.jwt.RefreshTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserRepository userRepository;

    @Transactional
    public void reissue(HttpServletRequest request, HttpServletResponse response) {
        String accessToken = jwtProvider.getJwtFromHeader(request, JwtProvider.AUTHORIZATION_HEADER);
        String refreshToken = jwtProvider.getJwtFromHeader(request, JwtProvider.REFRESH_HEADER);

        // 액세스가 만료되었는지 확인
        if(!jwtProvider.isExpiredAccessToken(accessToken)) {
            throw new AuthException(AuthErrorCode.NOT_EXPIRED_ACCESS_TOKEN);
        }

        // 리프레시 토큰 검증
        if(jwtProvider.validateTokenInternal(request, refreshToken)) {
            String userEmail = jwtProvider.getUserInfoFromClaims(refreshToken).getSubject();

            // 해당 리프레시 토큰이 데이터베이스에 존재하는지 확인
            if(!refreshTokenService.isRefreshTokenPresent(userEmail)) {
                throw new AuthException(AuthErrorCode.REFRESH_TOKEN_NOT_FOUND);
            }

            // 리프레시 토큰 기반으로 유저 찾기
            User findUser = userRepository.findByEmailOrElseThrow(userEmail);

            // 새 토큰 발급
            String newAccessToken = jwtProvider.createToken(findUser.getEmail(), JwtProvider.ACCESS_TOKEN_TIME, findUser.getUserType());
            String newRefreshToken = jwtProvider.createToken(findUser.getEmail(), JwtProvider.REFRESH_TOKEN_TIME, findUser.getUserType());

            refreshTokenService.saveRefreshTokenInfo(findUser.getEmail(), newRefreshToken);

            response.addHeader(JwtProvider.AUTHORIZATION_HEADER, newAccessToken);
            response.addHeader(JwtProvider.REFRESH_HEADER, newRefreshToken);

        } else {
            throw new AuthException(AuthErrorCode.INVALID_REFRESH_TOKEN);
        }
    }
}
