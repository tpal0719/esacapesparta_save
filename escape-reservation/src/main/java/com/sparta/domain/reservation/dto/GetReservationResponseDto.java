package com.sparta.domain.reservation.dto;

import com.sparta.domain.reservation.entity.PaymentStatus;
import com.sparta.domain.reservation.entity.Reservation;
import com.sparta.domain.reservation.entity.ReservationStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetReservationResponseDto {
    private Long reservationId;
    private String themeImage;
    private String themeTitle;
    private LocalDateTime createAt;
    private String storeName;
    private Integer player;
    private Long price;
    private PaymentStatus paymentStatus;
    private ReservationStatus reservationStatus;

    public GetReservationResponseDto(Reservation reservation){
        this.reservationId = reservation.getId();
        this.themeImage = reservation.getTheme().getThemeImage();
        this.themeTitle = reservation.getTheme().getTitle();
        this.createAt = reservation.getCreatedAt();
        this.storeName = reservation.getTheme().getStore().getName();
        this.player = reservation.getPlayer();
        this.price = reservation.getPrice();
        this.paymentStatus = reservation.getPaymentStatus();
        this.reservationStatus = reservation.getReservationStatus();
    }
}
