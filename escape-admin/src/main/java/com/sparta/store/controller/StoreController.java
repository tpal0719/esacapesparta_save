package com.sparta.store.controller;

import com.sparta.domain.store.dto.StoreCreateRequestDto;
import com.sparta.domain.store.dto.StoreResponseDto;
import com.sparta.domain.store.service.StoreAdminService;
import com.sparta.domain.user.entity.User;
import com.sparta.global.response.ResponseMessage;
import com.sparta.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class StoreController {

    private final StoreAdminService storeAdminService;

    // TODO : 방탈출 카페 강제 등록 for Admin
    @PostMapping("/stores")
    public ResponseEntity<ResponseMessage<StoreResponseDto>> createStoreByAdmin(StoreCreateRequestDto requestDto, User user) {

        StoreResponseDto responseDto = storeAdminService.createStoreByAdmin(requestDto, user);

        ResponseMessage<StoreResponseDto> responseMessage = ResponseMessage.<StoreResponseDto>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("관리자에 의해 방탈출 카페가 강제 등록 되었습니다.")
                .data(responseDto)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
    }

    // TODO : 모든 방탈출 카페 조회 (모든상태: 대기중,활성화,비활성화 ) for Admin
    @GetMapping("/stores")
    public ResponseEntity<ResponseMessage<List<StoreResponseDto>>> getAllStore(User user) {

        List<StoreResponseDto> responseDto = storeAdminService.getAllStore(user);

        ResponseMessage<List<StoreResponseDto>> responseMessage = ResponseMessage.<List<StoreResponseDto>>builder()
                .statusCode(HttpStatus.OK.value())
                .data(responseDto)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }


    // TODO : Admin이 방탈출 카페 등록 승인 ( PENDING -> ACTIVE )
    @PutMapping("/stores/{storeId}/approval")
    public ResponseEntity<ResponseMessage<Void>> approveStore(@Valid @PathVariable Long storeId, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        storeAdminService.approveStore(storeId, userDetails.getUser());

        ResponseMessage<Void> responseMessage = ResponseMessage.<Void>builder()
                .statusCode(HttpStatus.OK.value())
                .message("방탈출카페가 등록 되었습니다.")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }


    // TODO : 방탈출 카페 완전 삭제 for Admin
    @DeleteMapping
    public ResponseEntity<ResponseMessage<Void>> deleteStore(@Valid @RequestBody Long storeId,User user) {

        storeAdminService.deleteStore(storeId, user);

        ResponseMessage<Void> responseMessage = ResponseMessage.<Void>builder()
                .statusCode(HttpStatus.OK.value())
                .message("해당 방탈출카페가 삭제 되었습니다.")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }
}
