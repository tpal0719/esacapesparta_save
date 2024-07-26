package com.sparta.kakaopayment.dto;

import com.sparta.kakaopayment.entity.KakaoPayment;
import lombok.Getter;

@Getter
public class KakaoPaymentResponseDto {

    private Long id;
    private String tid;
    private String reservationId;
    private String userEmail;
    private String themeName;
    private String price;


    public KakaoPaymentResponseDto(KakaoPayment payment) {
        this.id = payment.getId();
        this.tid = payment.getTid();
        this.reservationId = payment.getReservationId();
        this.userEmail = payment.getUserEmail();
        this.themeName = payment.getThemeName();
        this.price = payment.getPrice();
    }

    public static String KakaoPaymentInfo(KakaoPayment kakaoPayment) {
        StringBuilder sb = new StringBuilder();
        sb.append("KakaoPayment Info: ");
        sb.append("Id: " + kakaoPayment.getId());
        sb.append("Tid: " + kakaoPayment.getId());
        sb.append("ReservationId: " + kakaoPayment.getReservationId());
        sb.append("UserEmail: " + kakaoPayment.getUserEmail());
        sb.append("ThemeName: " + kakaoPayment.getThemeName());
        sb.append("Price: " + kakaoPayment.getPrice());

        return sb.toString();
    }
}
