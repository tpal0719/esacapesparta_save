package com.sparta.domain.store.controller;

import com.sparta.domain.store.dto.request.StoreCreateRequestDto;
import com.sparta.domain.store.dto.request.StoreModifyRequestDto;
import com.sparta.domain.store.dto.response.StoreDetailResponseDto;
import com.sparta.domain.store.dto.response.StoreResponseDto;
import com.sparta.domain.store.service.StoreAdminService;
import com.sparta.global.response.ResponseMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class StoreAdminController {

    private final StoreAdminService storeAdminService;

    /**
     * TODO : 방탈출 카페 강제 등록 for Admin
     *
     * @param requestDto
     * @return StoreResponseDto : 방탈출 카페 정보
     * @author SEMI
     */
    @PostMapping("/stores")
    public ResponseEntity<ResponseMessage<StoreDetailResponseDto>> createStoreByAdmin(
            @RequestPart(value = "file", required = false) MultipartFile file,
            @Valid @RequestPart StoreCreateRequestDto requestDto
    ) {

        StoreDetailResponseDto responseDto = storeAdminService.createStoreByAdmin(file, requestDto);

        ResponseMessage<StoreDetailResponseDto> responseMessage = ResponseMessage.<StoreDetailResponseDto>builder()
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
    public ResponseEntity<ResponseMessage<Void>> approveStore(@Valid @PathVariable Long storeId) {

        storeAdminService.approveStore(storeId);

        ResponseMessage<Void> responseMessage = ResponseMessage.<Void>builder()
                .statusCode(HttpStatus.OK.value())
                .message("방탈출카페가 등록 되었습니다.")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    /**
     * 방탈출 카페 수정
     */
    @PutMapping("/{storeId}")
    public ResponseEntity<ResponseMessage<StoreDetailResponseDto>> modifyStore(
            @PathVariable Long storeId,
            @Valid @RequestBody StoreModifyRequestDto requestDto
    ) {
        StoreDetailResponseDto responseDto = storeAdminService.modifyStore(storeId, requestDto);

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
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        String imagePath = storeAdminService.modifyStoreImage(storeId, file);

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
            @PathVariable Long storeId
    ) {
        storeAdminService.deleteStoreImage(storeId);

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
    public ResponseEntity<ResponseMessage<Void>> deactivateStore(
            @PathVariable Long storeId
    ) {
        storeAdminService.deactivateStore(storeId);

        ResponseMessage<Void> responseMessage = ResponseMessage.<Void>builder()
                .statusCode(HttpStatus.OK.value())
                .message("방탈출 카페 비활성화가 완료되었습니다.")
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
    public ResponseEntity<ResponseMessage<Void>> deleteStore(@Valid @RequestBody Long storeId) {

        storeAdminService.deleteStore(storeId);

        ResponseMessage<Void> responseMessage = ResponseMessage.<Void>builder()
                .statusCode(HttpStatus.OK.value())
                .message("해당 방탈출카페가 삭제 되었습니다.")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }
}
