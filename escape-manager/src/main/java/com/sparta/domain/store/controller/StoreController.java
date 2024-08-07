package com.sparta.domain.store.controller;

import com.sparta.domain.store.dto.request.StoreModifyRequestDto;
import com.sparta.domain.store.dto.request.StoreRegisterRequestDto;
import com.sparta.domain.store.dto.response.StoreDetailResponseDto;
import com.sparta.domain.store.dto.response.StoreRegisterResponseDto;
import com.sparta.domain.store.dto.response.StoresGetResponseDto;
import com.sparta.domain.store.service.StoreService;
import com.sparta.global.response.ResponseMessage;
import com.sparta.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/manager/stores")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;

    /**
     * 방탈출 카페 등록 요청
     */
    @PostMapping
    public ResponseEntity<ResponseMessage<StoreRegisterResponseDto>> registerStore(
            @RequestPart(value = "file", required = false) MultipartFile file,
            @Valid @RequestPart StoreRegisterRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        StoreRegisterResponseDto responseDto = storeService.registerStore(file, requestDto, userDetails.getUser());

        ResponseMessage<StoreRegisterResponseDto> responseMessage = ResponseMessage.<StoreRegisterResponseDto>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("방탈출 카페 등록 요청이 완료되었습니다.")
                .data(responseDto)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
    }

    /**
     * 본인의 방탈출 카페 조회
     */
    @GetMapping
    public ResponseEntity<ResponseMessage<StoresGetResponseDto>> getMyStore(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        StoresGetResponseDto responseDto = storeService.getMyStore(userDetails.getUser());

        ResponseMessage<StoresGetResponseDto> responseMessage = ResponseMessage.<StoresGetResponseDto>builder()
                .statusCode(HttpStatus.OK.value())
                .message("본인의 방탈출 카페 조회가 완료되었습니다.")
                .data(responseDto)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    /**
     * 방탈출 카페 수정
     */
    @PutMapping("/{storeId}")
    public ResponseEntity<ResponseMessage<StoreDetailResponseDto>> modifyStore(
            @PathVariable Long storeId,
            @Valid @RequestBody StoreModifyRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        StoreDetailResponseDto responseDto = storeService.modifyStore(storeId, requestDto, userDetails.getUser());

        ResponseMessage<StoreDetailResponseDto> responseMessage = ResponseMessage.<StoreDetailResponseDto>builder()
                .statusCode(HttpStatus.OK.value())
                .message("방탈출 카페 수정이 완료되었습니다.")
                .data(responseDto)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    /**
     * 방탈출 카페 이미지 수정
     */
    @PutMapping("/{storeId}/image")
    public ResponseEntity<ResponseMessage<String>> modifyStoreImage(
            @PathVariable Long storeId,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        String imagePath = storeService.modifyStoreImage(storeId, file, userDetails.getUser());

        ResponseMessage<String> responseMessage = ResponseMessage.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message("방탈출 카페 이미지 수정이 완료되었습니다.")
                .data(imagePath)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    /**
     * 방탈출 카페 이미지 삭제
     */
    @DeleteMapping("/{storeId}/image")
    public ResponseEntity<ResponseMessage<Void>> deleteStoreImage(
            @PathVariable Long storeId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        storeService.deleteStoreImage(storeId, userDetails.getUser());

        ResponseMessage<Void> responseMessage = ResponseMessage.<Void>builder()
                .statusCode(HttpStatus.OK.value())
                .message("방탈출 카페 이미지 삭제가 완료되었습니다.")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    /**
     * 방탈출 카페 삭제
     */
    @DeleteMapping("/{storeId}")
    public ResponseEntity<ResponseMessage<Void>> deleteStore(
            @PathVariable Long storeId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        storeService.deleteStore(storeId, userDetails.getUser());

        ResponseMessage<Void> responseMessage = ResponseMessage.<Void>builder()
                .statusCode(HttpStatus.OK.value())
                .message("방탈출 카페 삭제가 완료되었습니다.")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

}