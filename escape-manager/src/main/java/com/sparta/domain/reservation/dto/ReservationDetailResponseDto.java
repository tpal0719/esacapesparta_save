package com.sparta.domain.reservation.dto;

import com.sparta.domain.reservation.entity.PaymentStatus;
import com.sparta.domain.reservation.entity.Reservation;
import com.sparta.domain.reservation.entity.ReservationStatus;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ReservationDetailResponseDto {

  private final Long reservationId;
  private final String storeName;
  private final String themeImage;
  private final String themeTitle;
  private final LocalDateTime startTime;
  private final Integer player;
  private final Long price;
  private final PaymentStatus paymentStatus;
  private final ReservationStatus reservationStatus;
  private final LocalDateTime createdAt;

  public ReservationDetailResponseDto(Reservation reservation) {
    this.reservationId = reservation.getId();
    this.themeImage = reservation.getTheme().getThemeImage();
    this.themeTitle = reservation.getTheme().getTitle();
    this.storeName = reservation.getTheme().getStore().getName();
    this.startTime = reservation.getThemeTime().getStartTime();
    this.player = reservation.getPlayer();
    this.price = reservation.getPrice();
    this.paymentStatus = reservation.getPaymentStatus();
    this.reservationStatus = reservation.getReservationStatus();
    this.createdAt = reservation.getCreatedAt();
  }
}
