package com.sparta.controller;

import com.sparta.domain.user.dto.UserResponseDto;
import com.sparta.domain.user.service.UserService;
import com.sparta.dto.request.EditProfileRequestDto;
import com.sparta.global.response.ResponseMessage;
import com.sparta.security.UserDetailsImpl;
import com.sparta.service.ConsumerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class ConsumerController {

    private final UserService userService;
    private final ConsumerService consumerService;

    // TODO : 로그인한 유저 프로필 조회
    @GetMapping("/profile")
    public ResponseEntity<ResponseMessage<UserResponseDto>> getProfile(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Long userId = userDetails.getUser().getId();
        UserResponseDto userResponseDto = consumerService.inquiryUser(userId);

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
            @RequestBody EditProfileRequestDto editProfileRequestDto) {

        Long userId = userDetails.getUser().getId();
        UserResponseDto responseDto = consumerService.editProfile(userId, editProfileRequestDto, userDetails.getUser());

        ResponseMessage<UserResponseDto> responseMessage = ResponseMessage.<UserResponseDto>builder()
                .statusCode(HttpStatus.OK.value())
                .message("프로필 수정이 완료되었습니다.")
                .data(responseDto)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }


}
