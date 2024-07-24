package com.sparta.controller;

import com.sparta.domain.user.entity.User;
import com.sparta.domain.user.repository.UserRepository;
import com.sparta.dto.request.StoreModifyRequestDto;
import com.sparta.dto.request.StoreRegisterRequestDto;
import com.sparta.dto.response.StoreModifyResponseDto;
import com.sparta.dto.response.StoresGetResponseDto;
import com.sparta.dto.response.StoreRegisterResponseDto;
import com.sparta.global.response.ResponseMessage;
import com.sparta.security.UserDetailsImpl;
import com.sparta.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manager/stores")
@Secured("MANAGER")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;

    /**
     * 방탈출 카페 등록 요청
     */
    @PostMapping
    public ResponseEntity<ResponseMessage<StoreRegisterResponseDto>> registerStore(
            @Valid @RequestBody StoreRegisterRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        StoreRegisterResponseDto responseDto = storeService.registerStore(requestDto, userDetails.getUser());

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
    public ResponseEntity<ResponseMessage<StoreModifyResponseDto>> modifyStore(
            @PathVariable Long storeId,
            @Valid @RequestBody StoreModifyRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        StoreModifyResponseDto responseDto = storeService.modifyStore(storeId, requestDto, userDetails.getUser());

        ResponseMessage<StoreModifyResponseDto> responseMessage = ResponseMessage.<StoreModifyResponseDto>builder()
                .statusCode(HttpStatus.OK.value())
                .message("방탈출 카페 수정이 완료되었습니다.")
                .data(responseDto)
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
