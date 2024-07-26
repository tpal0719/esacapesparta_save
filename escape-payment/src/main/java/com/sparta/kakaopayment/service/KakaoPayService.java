package com.sparta.kakaopayment.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class KakaoPayService {

    @Value("${kakao-payment.admin-key}")
    private String kakaoApiKey;
    @Value("${kakao-payment.cid}")
    private String cid;
    @Value("${kakao-payment.approve-url}")
    private String approveURL;
    @Value("${kakao-payment.cancel-url}")
    private String cancelURL;
    @Value("${kakao-payment.fail-url}")
    private String failURL;


    private static final String KAKAO_PAY_API_URL = "https://open-api.kakaopay.com/online/v1/payment/ready";

    public Map<String, Object> preparePayment(String orderId, String itemName, int quantity, int totalAmount) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "SECRET_KEY " + kakaoApiKey);

        Map<String, String> params = new HashMap<>();
        params.put("cid", "TC0ONETIME");
        params.put("partner_order_id", orderId);
        params.put("partner_user_id", "user_id");
        params.put("item_name", itemName);
        params.put("quantity", String.valueOf(quantity));
        params.put("total_amount", String.valueOf(totalAmount));
        params.put("vat_amount", "200");
        params.put("tax_free_amount", "0");
        params.put("approval_url", approveURL);
        params.put("cancel_url", cancelURL);
        params.put("fail_url", failURL);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(params, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(KAKAO_PAY_API_URL, entity, Map.class);

        return response.getBody();
    }
}