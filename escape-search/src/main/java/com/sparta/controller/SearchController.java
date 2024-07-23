package com.sparta.controller;

import com.sparta.domain.escapeRoom.dto.EscapeRoomResponseDto;
import com.sparta.domain.escapeRoom.service.EscapeRoomService;
import com.sparta.domain.store.dto.StoreResponseDto;
import com.sparta.domain.store.service.StoreService;
import com.sparta.global.response.ResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
@Slf4j
public class SearchController {
    private final StoreService storeService;
    private final EscapeRoomService escapeRoomService;

    /**
     * 방탈출 카페 조회
     * @param pageNum 페이지 번호
     * @param pageSize 페이지에 담는 데이터 수
     * @param isDesc 오름차순, 내림차순 정렬 기준
     * @param keyWord 검색 키워드
     * @param area 카페 지역
     * @param sort 속성별 정렬 기준
     * @return status.code, message, Store 리스트
     */
    @GetMapping("/stores")
    public ResponseEntity<ResponseMessage<Page<StoreResponseDto>>> getStores(
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(value = "isDesc", required = false, defaultValue = "false") boolean isDesc,
            @RequestParam(value = "keyWord", required = false) String keyWord,
            @RequestParam(value = "area", required = false, defaultValue = "") String area,
            @RequestParam(value = "sort", required = false, defaultValue = "name") String sort){

        Page<StoreResponseDto> stores = storeService.getStores(pageNum, pageSize, isDesc, keyWord, area, sort);

        ResponseMessage<Page<StoreResponseDto>> responseMessage = ResponseMessage.<Page<StoreResponseDto>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("방탈출 카페 조회에 성공했습니다.")
                .data(stores)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    /**
     * 방탈출 카페 조회
     * @param storeId 방탈출 카페 id
     * @param pageNum 페이지 번호
     * @param pageSize 페이지에 담는 데이터 수
     * @param isDesc 오름차순, 내림차순 정렬 기준
     * @param sort 속성별 정렬 기준
     * @return status.code, message, EscapeRoom 리스트
     */
    @GetMapping("/stores/{storeId}/escape-room")
    public ResponseEntity<ResponseMessage<Page<EscapeRoomResponseDto>>> getEscapeRoom(
            @PathVariable Long storeId,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(value = "isDesc", required = false, defaultValue = "false") boolean isDesc,
            @RequestParam(value = "sort", required = false, defaultValue = "title") String sort){

        Page<EscapeRoomResponseDto> escapeRooms = escapeRoomService.getEscapeRoom(storeId, pageNum, pageSize, isDesc, sort);

        ResponseMessage<Page<EscapeRoomResponseDto>> responseMessage = ResponseMessage.<Page<EscapeRoomResponseDto>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("방탈출 카페 테마 조회에 성공했습니다.")
                .data(escapeRooms)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);

    }
}




