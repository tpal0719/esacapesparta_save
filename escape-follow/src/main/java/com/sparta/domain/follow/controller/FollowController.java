package com.sparta.domain.follow.controller;

import com.sparta.domain.follow.service.FollowService;
import com.sparta.global.response.ResponseMessage;
import com.sparta.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/follow")
public class FollowController {

    private final FollowService followService;

    @PostMapping("/stores/{storeId}")
    public ResponseEntity<ResponseMessage<Void>> follow(
            @PathVariable Long storeId,
            @AuthenticationPrincipal UserDetailsImpl userDetails){

        followService.follow(storeId, userDetails.getUser());

        ResponseMessage<Void> responseMessage = ResponseMessage.<Void>builder()
                .statusCode(HttpStatus.OK.value())
                .data(null)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }
}
