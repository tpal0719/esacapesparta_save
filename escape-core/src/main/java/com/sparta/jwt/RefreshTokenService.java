package com.sparta.jwt;

import com.sparta.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public Optional<RefreshToken> findByEmail(String email) {
        return refreshTokenRepository.findByEmail(email); //
    }

    @Transactional
    public void saveRefreshToken(User user, String refreshToken) {
        Optional<RefreshToken> existToken = findByEmail(user.getEmail());

        if (existToken.isPresent()) {
            existToken.get().update(refreshToken);
        } else {
            refreshTokenRepository.save(new RefreshToken(user.getEmail(), refreshToken));
        }
    }

    @Transactional
    public void deleteToken(String email) {
        RefreshToken refreshToken = refreshTokenRepository.findByEmail(email).orElseThrow( //
                () -> new IllegalArgumentException("해당 사용자의 refresh Token이 존재하지 않습니다.")
        );
        refreshTokenRepository.delete(refreshToken);
    }
}
