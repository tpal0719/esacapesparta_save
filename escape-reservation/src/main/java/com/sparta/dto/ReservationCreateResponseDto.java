package com.sparta.dto;

import com.sparta.domain.reservation.entity.PaymentStatus;
import com.sparta.domain.reservation.entity.Reservation;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReservationCreateResponseDto {
    private Long reservationId;
    private Integer player;
    private Long price;
    private PaymentStatus paymentStatus;
    private LocalDateTime createAt;

    public ReservationCreateResponseDto(Reservation reservation){
        reservationId = reservation.getId();
        player = reservation.getPlayer();
        price = reservation.getPrice();
        paymentStatus = reservation.getPaymentStatus();
        createAt = reservation.getCreatedAt();
    }
}
