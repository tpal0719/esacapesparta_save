package com.sparta.user.controller;

import com.sparta.domain.store.dto.StoreResponseDto;
import com.sparta.domain.user.dto.UserResponseDto;
import com.sparta.domain.user.entity.User;
import com.sparta.global.response.ResponseMessage;
import com.sparta.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class UserController {

    private final UserService userService;

    //TODO : 모든 Manager 조회
    @GetMapping("/managers")
    public ResponseEntity<ResponseMessage<List<UserResponseDto>>> getAllManagers(User user) {

        List<UserResponseDto> responseDto = userService.getAllManagers(user);

        ResponseMessage<List<UserResponseDto>> responseMessage = ResponseMessage.<List<UserResponseDto>>builder()
                .statusCode(HttpStatus.OK.value())
                .data(responseDto)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    //TODO : 모든 Consumer 조회
    @GetMapping("/consumers")
    public ResponseEntity<ResponseMessage<List<UserResponseDto>>> getAllConsumers(User user) {

        List<UserResponseDto> responseDto = userService.getAllConsumers(user);

        ResponseMessage<List<UserResponseDto>> responseMessage = ResponseMessage.<List<UserResponseDto>>builder()
                .statusCode(HttpStatus.OK.value())
                .data(responseDto)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }


}
