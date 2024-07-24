package com.sparta.domain.user.controller;

import ch.qos.logback.classic.Logger;
import com.sparta.domain.user.dto.request.EmailVerificationRequestDto;
import com.sparta.domain.user.service.EmailService;
import com.sparta.domain.user.service.UserService;
import com.sparta.global.response.ResponseMessage;
import com.sparta.security.UserDetailsImpl;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users/mail")
public class EmailController {
    private final EmailService emailService;
    private final UserService userService;


    @PostMapping
    public ResponseEntity<ResponseMessage<String>> sendCertificationNumber(@AuthenticationPrincipal UserDetailsImpl userDetails)
            throws MessagingException, NoSuchAlgorithmException {
        String email = emailService.sendEmailForCertification(userDetails.getUser().getEmail());

        ResponseMessage<String> responseMessage = ResponseMessage.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message("입력한 이메일로 인증코드 발송했습니다.")
                .data(email)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @GetMapping
    public ResponseEntity<ResponseMessage<String>> verifyCertificationNumber(@Valid @RequestBody EmailVerificationRequestDto requestDTO,
                                                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("verifyCertificationNumber");
        emailService.verifyEmail(userDetails.getUser().getEmail(), requestDTO.getVerificationCode());
        userService.updateUserActive(userDetails.getUser());

        ResponseMessage<String> responseMessage = ResponseMessage.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message("이메일 인증이 완료되었습니다.")
                .data(userDetails.getUser().getEmail())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

}
