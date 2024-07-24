package com.sparta.controller;

import com.sparta.domain.user.entity.User;
import com.sparta.dto.request.ThemeCreateRequestDto;
import com.sparta.dto.request.ThemeModifyRequestDto;
import com.sparta.dto.response.ThemeDetailResponseDto;
import com.sparta.dto.response.ThemeGetResponseDto;
import com.sparta.global.response.ResponseMessage;
import com.sparta.service.ThemeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manager/stores")
@RequiredArgsConstructor
public class ThemeController {
    private final ThemeService themeService;

    @PostMapping("/themes")
    public ResponseEntity<ResponseMessage<ThemeDetailResponseDto>> createEscapeRoom(@RequestBody ThemeCreateRequestDto requestDto, User manager) {
        ThemeDetailResponseDto responseDto = themeService.createTheme(requestDto, manager);

        ResponseMessage<ThemeDetailResponseDto> responseMessage = ResponseMessage.<ThemeDetailResponseDto>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("방탈출 테마 등록이 완료되었습니다.")
                .data(responseDto)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
    }

    @GetMapping("/{storeId}/themes")
    public ResponseEntity<ResponseMessage<ThemeGetResponseDto>> getEscapeRooms(@PathVariable Long storeId, User manager) {
        ThemeGetResponseDto responseDto = themeService.getThemes(storeId, manager);

        ResponseMessage<ThemeGetResponseDto> responseMessage = ResponseMessage.<ThemeGetResponseDto>builder()
                .statusCode(HttpStatus.OK.value())
                .message("해당 방탈출 카페의 모든 테마 조회가 완료되었습니다.")
                .data(responseDto)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @PutMapping("/themes/{themeId}")
    public ResponseEntity<ResponseMessage<ThemeDetailResponseDto>> modifyEscapeRoom(@PathVariable Long themeId, @RequestBody ThemeModifyRequestDto requestDto, User manager) {
        ThemeDetailResponseDto responseDto = themeService.modifyTheme(themeId, requestDto, manager);

        ResponseMessage<ThemeDetailResponseDto> responseMessage = ResponseMessage.<ThemeDetailResponseDto>builder()
                .statusCode(HttpStatus.OK.value())
                .message("방탈출 테마 수정이 완료되었습니다.")
                .data(responseDto)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @DeleteMapping("/themes/{themeId}")
    public ResponseEntity<ResponseMessage<Void>> deleteEscapeRoom(@PathVariable Long themeId, User manager) {
        themeService.deleteTheme(themeId, manager);

        ResponseMessage<Void> responseMessage = ResponseMessage.<Void>builder()
                .statusCode(HttpStatus.OK.value())
                .message("방탈출 테마 삭제가 완료되었습니다.")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }
}
