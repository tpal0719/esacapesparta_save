package com.sparta.controller;

import com.sparta.dto.CreateReservationRequestDto;
import com.sparta.dto.CreateReservationResponseDto;
import com.sparta.dto.GetReservationResponseDto;
import com.sparta.global.response.ResponseMessage;
import com.sparta.security.UserDetailsImpl;
import com.sparta.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.type.ListType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ReservationController {

    private final ReservationService reservationService;

    //feign 예시
    @GetMapping("/lost")
    public String getLost(){
        return "hello!!!";
    }

    /**
     * 예약 생성
     * @param requestDto 예약 생성에 필요한 데이터
     * @param userDetails 로그인 유저
     * @return status.code, message, 예약
     */
    @PostMapping("/reservations")
    public ResponseEntity<ResponseMessage<CreateReservationResponseDto>> createReservation(
            @RequestBody CreateReservationRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        CreateReservationResponseDto responseDto =
                reservationService.createReservation(requestDto, userDetails.getUser());

        ResponseMessage<CreateReservationResponseDto> responseMessage = ResponseMessage.<CreateReservationResponseDto>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("예약에 성공했습니다.")
                .data(responseDto)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
    }


    /**
     * 예약 취소
     * @param reservationId 취소할 에약 id
     * @param userDetails 로그인 유저
     * @return status.code, message
     */
    @DeleteMapping("/reservations/{reservationId}")
    public ResponseEntity<ResponseMessage<Void>> deleteReservation(
            @PathVariable Long reservationId,
            @AuthenticationPrincipal UserDetailsImpl userDetails){

        reservationService.deleteReservation(reservationId, userDetails.getUser());

        ResponseMessage<Void> responseMessage = ResponseMessage.<Void>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("예약을 취소했습니다.")
                .data(null)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
    }

    /**
     * 예약 내역 조회
     * @param userDetails 로그인 유저
     * @return 예약 내역
     */
    @GetMapping("/reservations")
    public ResponseEntity<ResponseMessage<List<GetReservationResponseDto>>> getReservations(
            @AuthenticationPrincipal UserDetailsImpl userDetails){

        List<GetReservationResponseDto> responseDtoList = reservationService.getReservations(userDetails.getUser());

        ResponseMessage<List<GetReservationResponseDto>> responseMessage = ResponseMessage.<List<GetReservationResponseDto>>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("예약을 조회했습니다.")
                .data(responseDtoList)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
    }

}
