package com.sparta.controller;

import com.sparta.dto.response.ReservationsGetResponseDto;
import com.sparta.dto.response.StoresGetResponseDto;
import com.sparta.global.response.ResponseMessage;
import com.sparta.security.UserDetailsImpl;
import com.sparta.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Secured({"MANAGER", "ADMIN"})
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
}
