package com.sparta.store.controller;

import com.sparta.store.dto.StoreCreateRequestDto;
import com.sparta.store.dto.StoreResponseDto;
import com.sparta.global.response.ResponseMessage;
import com.sparta.store.service.StoreAdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class StoreController {

    private final StoreAdminService storeAdminService;

    /**
     * TODO : 방탈출 카페 강제 등록 for Admin
     *
     * @param requestDto
     * @return StoreResponseDto : 방탈출 카페 정보
     * @author SEMI
     */
    @PostMapping("/stores")
    @Secured("ADMIN")
    public ResponseEntity<ResponseMessage<StoreResponseDto>> createStoreByAdmin(StoreCreateRequestDto requestDto) {

        StoreResponseDto responseDto = storeAdminService.createStoreByAdmin(requestDto);

        ResponseMessage<StoreResponseDto> responseMessage = ResponseMessage.<StoreResponseDto>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("관리자에 의해 방탈출 카페가 강제 등록 되었습니다.")
                .data(responseDto)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
    }

    /**
     * TODO : 모든 방탈출 카페 조회 (모든상태: 대기중,활성화,비활성화 ) for Admin
     *
     * @return List<StoreResponseDto> : 방탈출 카페 정보 리스트
     * @author SEMI
     */
    @GetMapping("/stores")
    @Secured("ADMIN")
    public ResponseEntity<ResponseMessage<List<StoreResponseDto>>> getAllStore() {

        List<StoreResponseDto> responseDto = storeAdminService.getAllStore();

        ResponseMessage<List<StoreResponseDto>> responseMessage = ResponseMessage.<List<StoreResponseDto>>builder()
                .statusCode(HttpStatus.OK.value())
                .data(responseDto)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }


    /**
     * TODO : Admin이 방탈출 카페 등록 승인 ( PENDING -> ACTIVE )
     *
     * @param storeId
     * @author SEMI
     */
    @PutMapping("/stores/{storeId}/approval")
    @Secured("ADMIN")
    public ResponseEntity<ResponseMessage<Void>> approveStore(@Valid @PathVariable Long storeId) {

        storeAdminService.approveStore(storeId);

        ResponseMessage<Void> responseMessage = ResponseMessage.<Void>builder()
                .statusCode(HttpStatus.OK.value())
                .message("방탈출카페가 등록 되었습니다.")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }


    /**
     * TODO : 방탈출 카페 완전 삭제 for Admin
     *
     * @param storeId
     * @author SEMI
     */
    @DeleteMapping
    @Secured("ADMIN")
    public ResponseEntity<ResponseMessage<Void>> deleteStore(@Valid @RequestBody Long storeId) {

        storeAdminService.deleteStore(storeId);

        ResponseMessage<Void> responseMessage = ResponseMessage.<Void>builder()
                .statusCode(HttpStatus.OK.value())
                .message("해당 방탈출카페가 삭제 되었습니다.")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }
}
