package com.sparta.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.global.exception.errorCode.CommonErrorCode;
import com.sparta.global.exception.errorCode.SecurityErrorCode;
import com.sparta.global.response.ResponseErrorMessage;
import com.sparta.security.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        ResponseUtil.writeJsonErrorResponse(response, SecurityErrorCode.USER_FORBIDDEN);
    }
}