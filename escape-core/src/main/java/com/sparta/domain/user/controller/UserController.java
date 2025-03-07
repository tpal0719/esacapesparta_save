package com.sparta.domain.user.controller;

import com.sparta.domain.user.dto.request.SignupRequestDto;
import com.sparta.domain.user.dto.request.WithdrawRequestDto;
import com.sparta.domain.user.dto.response.SignupResponseDto;
import com.sparta.domain.user.service.UserService;
import com.sparta.global.response.ResponseMessage;
import com.sparta.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/core/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping("/signup")
  public ResponseEntity<ResponseMessage<SignupResponseDto>> createUser(
      @Valid @RequestBody SignupRequestDto requestDto) {
    SignupResponseDto signupResponseDto = userService.createUser(requestDto);

    ResponseMessage<SignupResponseDto> responseMessage = ResponseMessage.<SignupResponseDto>builder()
        .statusCode(HttpStatus.CREATED.value())
        .message("회원가입이 완료되었습니다.")
        .data(signupResponseDto)
        .build();

    return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
  }

  @PutMapping("/logout")
  public ResponseEntity<ResponseMessage<Long>> logout(
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    Long response = userService.logout(userDetails.getUser().getId());

    ResponseMessage<Long> responseMessage = ResponseMessage.<Long>builder()
        .statusCode(HttpStatus.OK.value())
        .message("로그아웃이 완료되었습니다.")
        .data(response)
        .build();
    return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
  }

  @PutMapping("/withdraw")
  public ResponseEntity<ResponseMessage<Long>> withdraw(
      @Valid @RequestBody WithdrawRequestDto withdrawRequestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {

    Long userId = userService.withdraw(withdrawRequestDto, userDetails.getUser().getId());

    ResponseMessage<Long> responseMessage = ResponseMessage.<Long>builder()
        .statusCode(HttpStatus.OK.value())
        .message("회원 탈퇴가 완료되었습니다.")
        .data(userId)
        .build();

    return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
  }

}
