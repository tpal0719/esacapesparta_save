package com.sparta.domain.kakaopayment.dto.response;

import com.sparta.domain.kakaopayment.entity.KakaoPayment;

public class KakaoPayInfoResponseDto {




    public static String getDatas(KakaoPayment kakaoPayment) {
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
