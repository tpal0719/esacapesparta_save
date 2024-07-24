package com.sparta.controller;

import com.sparta.dto.request.ThemeCreateRequestDto;
import com.sparta.dto.request.ThemeTimeCreateRequestDto;
import com.sparta.dto.response.ThemeDetailResponseDto;
import com.sparta.dto.response.ThemeTimeDetailResponseDto;
import com.sparta.global.response.ResponseMessage;
import com.sparta.security.UserDetailsImpl;
import com.sparta.service.ThemeTimeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Secured({"MANAGER", "ADMIN"})
@RequestMapping("/manager/stores/themes")
@RequiredArgsConstructor
public class ThemeTimeController {
    private final ThemeTimeService themeTimeService;

    @PostMapping("/{themeId}/theme-time")
    public ResponseEntity<ResponseMessage<ThemeTimeDetailResponseDto>> createThemeTime(
            @PathVariable Long themeId,
            @Valid @RequestBody ThemeTimeCreateRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        ThemeTimeDetailResponseDto responseDto = themeTimeService.createThemeTime(themeId, requestDto, userDetails.getUser());

        ResponseMessage<ThemeTimeDetailResponseDto> responseMessage = ResponseMessage.<ThemeTimeDetailResponseDto>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("방탈출 테마 예약 시간대 등록이 완료되었습니다.")
                .data(responseDto)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
    }
}
