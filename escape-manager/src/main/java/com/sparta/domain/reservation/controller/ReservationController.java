package com.sparta.domain.reservation.controller;

import com.sparta.domain.reservation.dto.ReservationsGetResponseDto;
import com.sparta.domain.reservation.service.ReservationService;
import com.sparta.global.response.ResponseMessage;
import com.sparta.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manager/stores/themes")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @GetMapping("/{themeId}/reservations")
    public ResponseEntity<ResponseMessage<ReservationsGetResponseDto>> getReservations(
            @PathVariable Long themeId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        ReservationsGetResponseDto responseDto = reservationService.getReservations(themeId, userDetails.getUser());

        ResponseMessage<ReservationsGetResponseDto> responseMessage = ResponseMessage.<ReservationsGetResponseDto>builder()
                .statusCode(HttpStatus.OK.value())
                .message("해당 방탈출 테마의 예약 내역 조회가 완료되었습니다.")
                .data(responseDto)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @DeleteMapping("/reservations/{reservationId}")
    public ResponseEntity<ResponseMessage<Void>> cancelReservation(
            @PathVariable Long reservationId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        reservationService.cancelReservation(reservationId, userDetails.getUser());

        ResponseMessage<Void> responseMessage = ResponseMessage.<Void>builder()
                .statusCode(HttpStatus.OK.value())
                .message("해당 예약 취소가 완료되었습니다.")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }
}