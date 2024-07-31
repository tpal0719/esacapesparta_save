package com.sparta.domain.user.controller;

import com.sparta.domain.user.dto.request.CertificateRequestDto;
import com.sparta.domain.user.service.EmailService;
import com.sparta.domain.user.service.UserService;
import com.sparta.global.response.ResponseMessage;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users/mail")
public class EmailController {

    private final EmailService emailService;
    private final UserService userService;


    // TODO : 이메일 인증번호 발송
    @PostMapping
    public ResponseEntity<ResponseMessage<String>> sendCertificationNumber(
            @Valid @RequestBody CertificateRequestDto requestDto)
            throws MessagingException, NoSuchAlgorithmException {

        String email = emailService.sendEmailForCertification(requestDto);

        ResponseMessage<String> responseMessage = ResponseMessage.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message("입력한 이메일로 인증코드 발송했습니다.")
                .data(email)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

//    // TODO : 이메일 인증번호 확인
//    @GetMapping
//    public ResponseEntity<ResponseMessage<String>> verifyCertificationNumber(
//            @Valid @RequestBody EmailVerificationRequestDto requestDTO
//    ) {
//        emailService.verifyEmail(requestDTO.getEmail(), requestDTO.getVerificationCode());
//
//        ResponseMessage<String> responseMessage = ResponseMessage.<String>builder()
//                .statusCode(HttpStatus.OK.value())
//                .message("이메일 인증이 완료되었습니다.")
//                .data(requestDTO.getEmail())
//                .build();
//
//        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
//    }

//    // TODO : 초대 코드 이메일 발송
//    @PostMapping("/invite")
//    public ResponseEntity<ResponseMessage<String>> sendInviteCode(@RequestBody InviteRequestDto requestDto) throws MessagingException {
//        String inviteCode = emailService.createInviteCode(requestDto.getUserType());
//        emailService.sendInviteCode(requestDto.getEmail(), inviteCode);
//
//        ResponseMessage<String> responseMessage = ResponseMessage.<String>builder()
//                .statusCode(HttpStatus.OK.value())
//                .message("초대 코드가 생성되어 발송되었습니다.")
//                .data(requestDto.getEmail())
//                .build();
//
//        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
//    }
}
