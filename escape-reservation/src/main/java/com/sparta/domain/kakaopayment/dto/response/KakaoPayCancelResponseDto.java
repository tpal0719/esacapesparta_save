package com.sparta.domain.kakaopayment.dto.response;

import java.util.Map;

public class KakaoPayCancelResponseDto {
    public String aid;
    public String tid;
    public String cid;
    public String status;  // 참고 https://developers.kakaopay.com/docs/payment/online/cancellation

    public String paymentId;
    public String consumerId;
    public String paymentType;
    public String cancelPrice;
    public String themeName;
    public String themeId;

    public String createdAt;
    public String approvedAt;
    public String canceledAt;
    public String payload;


    public KakaoPayCancelResponseDto(Map<String, Object> jsonValue) {
        this.aid = jsonValue.get("aid").toString();
        this.tid = jsonValue.get("tid").toString();
        this.cid = jsonValue.get("cid").toString();
        this.status = jsonValue.get("status").toString();
        this.paymentId = jsonValue.get("partner_order_id").toString();
        this.consumerId = jsonValue.get("partner_user_id").toString();
        this.paymentType = jsonValue.get("payment_method_type").toString();
        this.cancelPrice = jsonValue.get("amount").toString();
        this.themeName = jsonValue.get("item_name").toString();
        this.themeId = jsonValue.get("item_code").toString();
        this.createdAt = jsonValue.get("created_at").toString();
        this.approvedAt = jsonValue.get("approved_at").toString();
        this.canceledAt = jsonValue.get("canceled_at").toString();
        this.payload = jsonValue.get("payload").toString();
        //진짜 징그럽네
    }
}
