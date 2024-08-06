package com.sparta.domain.user.controller;

import com.sparta.domain.user.service.AuthService;
import com.sparta.global.response.ResponseMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/auth/reissue")
    public ResponseEntity<ResponseMessage<Void>> reissue(HttpServletRequest request, HttpServletResponse response) {
        authService.reissue(request, response);

        ResponseMessage<Void> responseMessage = ResponseMessage.<Void>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Access Token 재발급이 완료되었습니다.")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }
}
