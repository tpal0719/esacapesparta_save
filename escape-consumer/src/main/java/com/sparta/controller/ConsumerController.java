package com.sparta.controller;

import com.sparta.domain.user.dto.UserResponseDto;
import com.sparta.domain.user.entity.User;
import com.sparta.domain.user.service.UserService;
import com.sparta.dto.request.EditPasswordRequestDto;
import com.sparta.dto.request.EditProfileRequestDto;
import com.sparta.global.response.ResponseMessage;
import com.sparta.security.UserDetailsImpl;
import com.sparta.service.ConsumerService;
import jakarta.validation.Valid;
import com.sparta.feign.EscapeReservationClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class ConsumerController {

    private final UserService userService;
    private final ConsumerService consumerService;

    // TODO : 로그인한 유저 프로필 조회
    @GetMapping("/profile")
    public ResponseEntity<ResponseMessage<UserResponseDto>> inquiryProfile(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        User user = userDetails.getUser();
        UserResponseDto userResponseDto = consumerService.inquiryUser(user);

        ResponseMessage<UserResponseDto> responseMessage = ResponseMessage.<UserResponseDto>builder()
                .statusCode(HttpStatus.OK.value())
                .message("프로필 조회가 완료되었습니다.")
                .data(userResponseDto)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    // TODO : 유저 프로필 수정
    @PutMapping("/profile")
    public ResponseEntity<ResponseMessage<UserResponseDto>> modifyProfile(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody EditProfileRequestDto editProfileRequestDto) {

        User user = userDetails.getUser();
        UserResponseDto responseDto = consumerService.modifyProfile(editProfileRequestDto, user);

        ResponseMessage<UserResponseDto> responseMessage = ResponseMessage.<UserResponseDto>builder()
                .statusCode(HttpStatus.OK.value())
                .message("프로필 수정이 완료되었습니다.")
                .data(responseDto)
                .build();
    private final EscapeReservationClient escapeReservationClient;

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    @GetMapping("/reservations")
    public String getReservation(){
        log.error("hihihi");
        return escapeReservationClient.getLost();
    }

    // TODO : 비밀번호 수정
    @PutMapping("/profile/password")
    public ResponseEntity<ResponseMessage<UserResponseDto>> editPassword(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody EditPasswordRequestDto requestDTO) {

        User user = userDetails.getUser();
        UserResponseDto responseDto = consumerService.editPassword(requestDTO, user);

        ResponseMessage<UserResponseDto> responseMessage = ResponseMessage.<UserResponseDto>builder()
                .statusCode(HttpStatus.OK.value())
                .message("비밀번호 수정이 완료되었습니다.")
                .data(responseDto)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }
}

