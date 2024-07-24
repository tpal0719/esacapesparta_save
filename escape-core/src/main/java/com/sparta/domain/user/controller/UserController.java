package com.sparta.domain.user.controller;

import com.sparta.domain.user.dto.request.SignupRequestDto;
import com.sparta.domain.user.dto.response.SignupResponseDto;
import com.sparta.domain.user.service.UserService;
import com.sparta.global.response.ResponseMessage;
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
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    /**
     * 회원 가입
     *
     * @param requestDto
     * @return 유저 정보 + 상태 코드를 반환
     */
    @PostMapping("/signup")
    public ResponseEntity<ResponseMessage<SignupResponseDto>> createUser(@Valid @RequestBody SignupRequestDto requestDto) {
        SignupResponseDto signupResponseDto = userService.createUser(requestDto);

        ResponseMessage<SignupResponseDto> responseMessage = ResponseMessage.<SignupResponseDto>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("회원가입이 완료되었습니다.")
                .data(signupResponseDto)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
    }
}
