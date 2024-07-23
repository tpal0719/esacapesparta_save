package com.sparta.controller;

import com.sparta.domain.user.entity.User;
import com.sparta.dto.request.EscapeRoomCreateRequestDto;
import com.sparta.dto.request.EscapeRoomModifyRequestDto;
import com.sparta.dto.response.EscapeRoomDetailResponseDto;
import com.sparta.dto.response.EscapeRoomsGetResponseDto;
import com.sparta.global.response.ResponseMessage;
import com.sparta.service.EscapeRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manager/stores")
@RequiredArgsConstructor
public class EscapeRoomController {
    private final EscapeRoomService escapeRoomService;

    @PostMapping("/themes")
    public ResponseEntity<ResponseMessage<EscapeRoomDetailResponseDto>> createEscapeRoom(@RequestBody EscapeRoomCreateRequestDto requestDto, User manager) {
        EscapeRoomDetailResponseDto responseDto = escapeRoomService.createEscapeRoom(requestDto, manager);

        ResponseMessage<EscapeRoomDetailResponseDto> responseMessage = ResponseMessage.<EscapeRoomDetailResponseDto>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("방탈출 테마 등록이 완료되었습니다.")
                .data(responseDto)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
    }

    @GetMapping("/{storeId}/themes")
    public ResponseEntity<ResponseMessage<EscapeRoomsGetResponseDto>> getEscapeRooms(@PathVariable Long storeId, User manager) {
        EscapeRoomsGetResponseDto responseDto = escapeRoomService.getEscapeRooms(storeId, manager);

        ResponseMessage<EscapeRoomsGetResponseDto> responseMessage = ResponseMessage.<EscapeRoomsGetResponseDto>builder()
                .statusCode(HttpStatus.OK.value())
                .message("해당 방탈출 카페의 모든 테마 조회가 완료되었습니다.")
                .data(responseDto)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @PutMapping("/themes/{themeId}")
    public ResponseEntity<ResponseMessage<EscapeRoomDetailResponseDto>> modifyEscapeRoom(@PathVariable Long themeId, @RequestBody EscapeRoomModifyRequestDto requestDto, User manager) {
        EscapeRoomDetailResponseDto responseDto = escapeRoomService.modifyEscapeRoom(themeId, requestDto, manager);

        ResponseMessage<EscapeRoomDetailResponseDto> responseMessage = ResponseMessage.<EscapeRoomDetailResponseDto>builder()
                .statusCode(HttpStatus.OK.value())
                .message("방탈출 테마 수정이 완료되었습니다.")
                .data(responseDto)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @DeleteMapping("/themes/{themeId}")
    public ResponseEntity<ResponseMessage<Void>> deleteEscapeRoom(@PathVariable Long themeId, User manager) {
        escapeRoomService.deleteEscapeRoom(themeId, manager);

        ResponseMessage<Void> responseMessage = ResponseMessage.<Void>builder()
                .statusCode(HttpStatus.OK.value())
                .message("방탈출 테마 삭제가 완료되었습니다.")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }
}
