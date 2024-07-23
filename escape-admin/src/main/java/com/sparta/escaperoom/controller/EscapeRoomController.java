package com.sparta.escaperoom.controller;

import com.sparta.domain.escapeRoom.dto.EscapeRoomResponseDto;
import com.sparta.domain.escapeRoom.service.EscapeRoomService;
import com.sparta.global.response.ResponseMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class EscapeRoomController {
    private final EscapeRoomService escaperoomService;

    @PutMapping
    public ResponseEntity<ResponseMessage<Void>> approveEscapeRoom(@Valid @RequestBody EscapeRoomResponseDto responseDto/*, @AuthenticationPrincipal UserDetailsImpl userDetails*/) {

        ResponseMessage<Void> responseMessage = ResponseMessage.<Void>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("장바구니에 메뉴가 추가되었습니다.")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
    }

}