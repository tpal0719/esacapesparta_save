package com.sparta.controller;

import com.sparta.dto.request.ThemeCreateRequestDto;
import com.sparta.dto.request.ThemeModifyRequestDto;
import com.sparta.dto.response.ThemeDetailResponseDto;
import com.sparta.dto.response.ThemeGetResponseDto;
import com.sparta.global.response.ResponseMessage;
import com.sparta.security.UserDetailsImpl;
import com.sparta.service.ThemeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/manager/stores")
@Secured({"MANAGER", "ADMIN"})
@RequiredArgsConstructor
public class ThemeController {
    private final ThemeService themeService;

    @PostMapping("/themes")
    public ResponseEntity<ResponseMessage<ThemeDetailResponseDto>> createTheme(
            @RequestPart(value = "file", required = false) MultipartFile file,
            @Valid @RequestPart ThemeCreateRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        ThemeDetailResponseDto responseDto = themeService.createTheme(file, requestDto, userDetails.getUser());

        ResponseMessage<ThemeDetailResponseDto> responseMessage = ResponseMessage.<ThemeDetailResponseDto>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("방탈출 테마 등록이 완료되었습니다.")
                .data(responseDto)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
    }

    @GetMapping("/{storeId}/themes")
    public ResponseEntity<ResponseMessage<ThemeGetResponseDto>> getThemes(
            @PathVariable Long storeId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        ThemeGetResponseDto responseDto = themeService.getThemes(storeId, userDetails.getUser());

        ResponseMessage<ThemeGetResponseDto> responseMessage = ResponseMessage.<ThemeGetResponseDto>builder()
                .statusCode(HttpStatus.OK.value())
                .message("해당 방탈출 카페의 모든 테마 조회가 완료되었습니다.")
                .data(responseDto)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @PutMapping("/themes/{themeId}")
    public ResponseEntity<ResponseMessage<ThemeDetailResponseDto>> modifyTheme(
            @PathVariable Long themeId,
            @Valid @RequestBody ThemeModifyRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        ThemeDetailResponseDto responseDto = themeService.modifyTheme(themeId, requestDto, userDetails.getUser());

        ResponseMessage<ThemeDetailResponseDto> responseMessage = ResponseMessage.<ThemeDetailResponseDto>builder()
                .statusCode(HttpStatus.OK.value())
                .message("방탈출 테마 수정이 완료되었습니다.")
                .data(responseDto)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    /**
     * 방탈출 테마 이미지 수정
     */
    @PutMapping("/themes/{themeId}/image")
    @Secured({"MANAGER", "ADMIN"})
    public ResponseEntity<ResponseMessage<String>> modifyThemeImage(
            @PathVariable Long themeId,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        String imagePath = themeService.modifyThemeImage(themeId, file, userDetails.getUser());

        ResponseMessage<String> responseMessage = ResponseMessage.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message("방탈출 테마 이미지 수정이 완료되었습니다.")
                .data(imagePath)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    /**
     * 방탈출 테마 이미지 삭제
     */
    @DeleteMapping("/themes/{themeId}/image")
    @Secured({"MANAGER", "ADMIN"})
    public ResponseEntity<ResponseMessage<Void>> deleteThemeImage(
            @PathVariable Long themeId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        themeService.deleteThemeImage(themeId, userDetails.getUser());

        ResponseMessage<Void> responseMessage = ResponseMessage.<Void>builder()
                .statusCode(HttpStatus.OK.value())
                .message("방탈출 카페 이미지 삭제가 완료되었습니다.")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    /**
     * 방탈출 테마 완전히 삭제
     */
    @DeleteMapping("/themes/{themeId}")
    public ResponseEntity<ResponseMessage<Void>> deleteTheme(
            @PathVariable Long themeId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        themeService.deleteTheme(themeId, userDetails.getUser());

        ResponseMessage<Void> responseMessage = ResponseMessage.<Void>builder()
                .statusCode(HttpStatus.OK.value())
                .message("방탈출 테마 삭제가 완료되었습니다.")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    /**
     * 방탈출 테마 상태 변경
     */
    @PutMapping("themes/{themeId}/status")
    public ResponseEntity<ResponseMessage<Void>> changeThemeStatus(
            @PathVariable Long themeId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        themeService.changeThemeStatus(themeId, userDetails.getUser());

        ResponseMessage<Void> responseMessage = ResponseMessage.<Void>builder()
                .statusCode(HttpStatus.OK.value())
                .message("방탈출 테마 상태 변경이 완료되었습니다.")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }
}
