package com.sparta.domain.kakaopayment.dto.request;

import com.sparta.domain.kakaopayment.entity.KakaoPayment;

public class KakaoPayCancelRequestDto {

    public String cid;
//    public String cid_secret;
    public String tid;
    public Integer cancelAmount;
    public Integer cancelTaxFreeAmount;

    public KakaoPayCancelRequestDto(KakaoPayment kakaoPayment) {
        this.cid = kakaoPayment.getCid();
        this.tid = kakaoPayment.getTid();
        this.cancelAmount = 1;
        this.cancelTaxFreeAmount = 0;

    }
}
