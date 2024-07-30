package com.sparta.security.filter;

import com.sparta.global.exception.customException.CustomSecurityException;
import com.sparta.global.exception.errorCode.SecurityErrorCode;
import com.sparta.jwt.JwtProvider;
import com.sparta.security.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.sparta.jwt.JwtProvider.*;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    private final UserDetailsServiceImpl userDetailsService;

    /**
     * 토큰 검증
     */
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = jwtProvider.getJwtFromHeader(req, AUTHORIZATION_HEADER);
        String refreshToken = jwtProvider.getJwtFromHeader(req, REFRESH_HEADER);

        if(StringUtils.hasText(accessToken)) {
            if(jwtProvider.validateTokenInternal(req, accessToken)) {
                log.info("Access Token 검증 성공");
                // 해당 유저의 리프레시 토큰이 존재하는지 검증 필요..?
                Claims info = jwtProvider.getUserInfoFromClaims(accessToken);
                setAuthentication(info.getSubject());
            }
        }
        filterChain.doFilter(req, res);
    }

    /**
     * 인증 처리
     */
    public void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    /**
     * 인증 객체 생성
     */
    private Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

//    /**
//     * HttpResponse에 Json 형태로 응답
//     */
//    private void writeJsonResponse(HttpServletResponse response, HttpStatus status, String message, String data) throws IOException {
//        ResponseMessage<String>responseMessage = ResponseMessage.<String>builder()
//                .statusCode(status.value())
//                .message(message)
//                .data(data)
//                .build();
//
//        String jsonResponse = new ObjectMapper().writeValueAsString(responseMessage);
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType("application/json; charset=UTF-8");
//        response.getWriter().write(jsonResponse);
//    }
}
