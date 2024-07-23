package com.sparta.domain.store.controller;

import com.sparta.domain.store.dto.StoreRequestParamDto;
import com.sparta.domain.store.dto.StoreResponseDto;
import com.sparta.domain.store.service.StoreService;
import com.sparta.domain.user.entity.User;
import com.sparta.domain.user.repository.UserRepository;
import com.sparta.global.response.BasicResponse;
import com.sparta.global.response.ResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
@Slf4j
public class StoreController {
    private final StoreService storeService;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<ResponseMessage<List<StoreResponseDto>>> getStores(
//            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @ModelAttribute StoreRequestParamDto storeRequestParamDto){

        User user = userRepository.findById(1L).orElse(null);
        List<StoreResponseDto> storeResponseDtoList = storeService.getStores(user, storeRequestParamDto);

        ResponseMessage<List<StoreResponseDto>> responseMessage = ResponseMessage.<List<StoreResponseDto>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("방탈출 카페 조회에 성공했습니다.")
                .data(storeResponseDtoList)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

}




