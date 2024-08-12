package com.sparta.domain.user.controller;

import com.sparta.domain.user.dto.request.CertificateRequestDto;
import com.sparta.domain.user.service.EmailService;
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

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/core/users/mail")
public class EmailController {

  private final EmailService emailService;

  @PostMapping
  public ResponseEntity<ResponseMessage<String>> sendCertificationNumber(
      @Valid @RequestBody CertificateRequestDto requestDto)
      throws MessagingException {

    String email = emailService.sendEmailForCertification(requestDto);

    ResponseMessage<String> responseMessage = ResponseMessage.<String>builder()
        .statusCode(HttpStatus.OK.value())
        .message("입력한 이메일로 인증코드를 발송했습니다.")
        .data(email)
        .build();

    return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
  }
}
