package com.sparta.domain.store.controller;

import com.sparta.domain.store.dto.StoreRequestParamDto;
import com.sparta.domain.store.dto.StoreResponseDto;
import com.sparta.domain.store.service.StoreService;
import com.sparta.domain.user.repository.UserRepository;
import com.sparta.global.response.ResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
@Slf4j
public class StoreController {
    private final StoreService storeService;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<ResponseMessage<Page<StoreResponseDto>>> getStores(
            @ModelAttribute StoreRequestParamDto storeRequestParamDto){

        Page<StoreResponseDto> storeResponseDtoList = storeService.getStores(storeRequestParamDto);

        ResponseMessage<Page<StoreResponseDto>> responseMessage = ResponseMessage.<Page<StoreResponseDto>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("방탈출 카페 조회에 성공했습니다.")
                .data(storeResponseDtoList)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

}




