package com.sparta.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.domain.user.dto.request.LoginRequestDto;
import com.sparta.domain.user.entity.UserType;
import com.sparta.global.response.ResponseMessage;
import com.sparta.jwt.JwtProvider;
import com.sparta.jwt.RefreshTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

import static com.sparta.jwt.JwtProvider.*;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtProvider jwtProvider;

    private final RefreshTokenService refreshTokenService;

    public JwtAuthenticationFilter(JwtProvider jwtProvider, RefreshTokenService refreshTokenService) {
        this.jwtProvider = jwtProvider;
        this.refreshTokenService = refreshTokenService;
        setFilterProcessesUrl("/users/login");
    }

    /**
     * 로그인 시도
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequestDto requestDto = new ObjectMapper()
                    .readValue(request.getInputStream(), LoginRequestDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getEmail(),
                            requestDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 로그인 성공 및 JWT생성
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();
        UserType role = userDetails.getUser().getUserType();

        String username = userDetails.getUsername();
        String accessToken = jwtProvider.createToken(username, TOKEN_TIME, role);
        String refreshToken = jwtProvider.createToken(username, REFRESH_TOKEN_TIME, role);

        //refresh토큰 저장메서드 추가
        refreshTokenService.saveRefreshToken(userDetails.getUser(), refreshToken.substring(BEAR.length()));

        // 응답 헤더에 토큰 추가
        response.addHeader(JwtProvider.AUTHORIZATION_HEADER, accessToken);
        response.addHeader(JwtProvider.REFRESH_HEADER, refreshToken);

        // JSON 응답 작성
        writeJsonResponse(response, HttpStatus.OK, "로그인에 성공했습니다.", authResult.getName());

        log.info("User = {}, message = {}", username, "로그인에 성공했습니다.");
    }

    /**
     * 로그인 실패
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        log.info("로그인 실패 : {}", failed.getMessage());

        writeJsonResponse(response, HttpStatus.BAD_REQUEST, "로그인에 실패했습니다.", "");
    }

    /**
     * HttpResponse에 JSON 형태로 응답하기
     */
    private void writeJsonResponse(HttpServletResponse response, HttpStatus status, String message, String data) throws IOException {
        ResponseMessage responseMessage = ResponseMessage.<String>builder()
                .statusCode(status.value())
                .message(message)
                .data(data)
                .build();

        String jsonResponse = new ObjectMapper().writeValueAsString(responseMessage);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(jsonResponse);
    }
}
