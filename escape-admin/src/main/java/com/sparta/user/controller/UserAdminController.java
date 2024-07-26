package com.sparta.user.controller;

import com.sparta.domain.user.dto.UserResponseDto;
import com.sparta.global.response.ResponseMessage;
import com.sparta.user.service.UserAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class UserAdminController {

    private final UserAdminService userService;

    /**
     * TODO : 모든 Manager 조회
     *
     * @author SEMI
     */
    @GetMapping("/managers")
    @Secured("ADMIN")
    public ResponseEntity<ResponseMessage<List<UserResponseDto>>> getAllManagers() {

        List<UserResponseDto> responseDto = userService.getAllManagers();

        ResponseMessage<List<UserResponseDto>> responseMessage = ResponseMessage.<List<UserResponseDto>>builder()
                .statusCode(HttpStatus.OK.value())
                .data(responseDto)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    /**
     * TODO : 모든 Consumer 조회
     *
     * @author SEMI
     */
    @GetMapping("/consumers")
    @Secured("ADMIN")
    public ResponseEntity<ResponseMessage<List<UserResponseDto>>> getAllConsumers() {

        List<UserResponseDto> responseDto = userService.getAllConsumers();

        ResponseMessage<List<UserResponseDto>> responseMessage = ResponseMessage.<List<UserResponseDto>>builder()
                .statusCode(HttpStatus.OK.value())
                .data(responseDto)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }


}
