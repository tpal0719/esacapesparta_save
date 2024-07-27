package com.sparta.domain.kakaopayment.dto.response;

import com.sparta.domain.kakaopayment.entity.KakaoPayment;
import lombok.Getter;

@Getter
public class KakaoPayResponseDto {

    private Long id;
    private String tid;
    private String reservationId;
    private String userEmail;
    private String themeName;
    private String price;


    public KakaoPayResponseDto(KakaoPayment payment) {
        this.id = payment.getId();
        this.tid = payment.getTid();
        this.reservationId = payment.getReservationId();
        this.userEmail = payment.getUserEmail();
        this.themeName = payment.getThemeName();
        this.price = payment.getPrice();
    }

}
