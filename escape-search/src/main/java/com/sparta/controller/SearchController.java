package com.sparta.controller;

import com.sparta.domain.store.entity.StoreRegion;
import com.sparta.domain.theme.dto.ThemeInfoResponseDto;
import com.sparta.domain.theme.dto.ThemeResponseDto;
import com.sparta.domain.theme.dto.ThemeTimeResponseDto;
import com.sparta.domain.theme.service.ThemeService;
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
    private final ThemeService themeService;

    /**
     * 방탈출 카페 조회
     * @param pageNum 페이지 번호
     * @param pageSize 페이지에 담는 데이터 수
     * @param isDesc 오름차순, 내림차순 정렬 기준
     * @param keyWord 검색 키워드
     * @param storeRegion 카페 지역
     * @param sort 속성별 정렬 기준
     * @return status.code, message, Store 리스트
     */
    @GetMapping("/stores")
    public ResponseEntity<ResponseMessage<Page<StoreResponseDto>>> getStores(
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(value = "isDesc", required = false, defaultValue = "false") boolean isDesc,
            @RequestParam(value = "keyWord", required = false) String keyWord,
            @RequestParam(value = "storeRegion", required = false, defaultValue = "ALL") StoreRegion storeRegion,
            @RequestParam(value = "sort", required = false, defaultValue = "name") String sort){

        Page<StoreResponseDto> stores = storeService.getStores(pageNum, pageSize, isDesc, keyWord, storeRegion, sort);

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
    @GetMapping("/stores/{storeId}/theme")
    public ResponseEntity<ResponseMessage<Page<ThemeResponseDto>>> getTheme(
            @PathVariable Long storeId,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(value = "isDesc", required = false, defaultValue = "false") boolean isDesc,
            @RequestParam(value = "sort", required = false, defaultValue = "title") String sort){

        Page<ThemeResponseDto> themes = themeService.getTheme(storeId, pageNum, pageSize, isDesc, sort);

        ResponseMessage<Page<ThemeResponseDto>> responseMessage = ResponseMessage.<Page<ThemeResponseDto>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("방탈출 카페 테마 조회에 성공했습니다.")
                .data(themes)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @GetMapping("/stores/theme/{themeId}/info")
    public ResponseEntity<ResponseMessage<ThemeInfoResponseDto>> getThemeInfo(
            @PathVariable Long themeId) {

        return
    }


    @GetMapping("/stores/theme/{themeId}/time")
    public ResponseEntity<ResponseMessage<ThemeTimeResponseDto>>
}




