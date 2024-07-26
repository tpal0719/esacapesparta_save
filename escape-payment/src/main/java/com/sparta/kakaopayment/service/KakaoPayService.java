package com.sparta.kakaopayment.service;

import com.sparta.domain.reservation.entity.Reservation;
import com.sparta.domain.reservation.repository.ReservationRepository;
import com.sparta.domain.user.entity.User;
import com.sparta.kakaopayment.dto.KakaoPaymentResponseDto;
import com.sparta.kakaopayment.entity.KakaoPayment;
import com.sparta.kakaopayment.repository.PaymentRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequiredArgsConstructor
@Slf4j
public class KakaoPayService {

    private final ReservationRepository reservationRepository;
    private final PaymentRepository paymentRepository;

    @Value("${kakao-payment.admin-key}")
    private String kakaoApiKey; //kakao api key
    @Value("${kakao-payment.cid}")
    private String cid; // 가맹점 코드
    @Value("${kakao-payment.approve-url}")
    private String approveURL; //결제창 넘겨주기
    @Value("${kakao-payment.cancel-url}")
    private String cancelURL; // 결제실패
    @Value("${kakao-payment.fail-url}")
    private String failURL; //결제완료


    private static final String KAKAO_PAY_API_URL = "https://open-api.kakaopay.com/online/v1/payment/ready";


    /**
     * TODO: 결제할 수 있는 URL 넘겨줌
     * @param reservationId 예약정보만 넘겨줌
     * @return key - value format json
     * @author SEMI
     */
    public Map<String, Object> preparePayment(Long reservationId) {
        Reservation reservation = reservationRepository.findByIdOrElse(reservationId);

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "SECRET_KEY " + kakaoApiKey);

        Map<String, String> params = new HashMap<>();
        params.put("cid", cid);
        params.put("partner_order_id", String.valueOf(reservationId));
        params.put("partner_user_id", String.valueOf(reservation.getUser().getEmail()));
        params.put("item_name", reservation.getTheme().getTitle() + " / "+
                reservation.getPlayer()+"인 / "+
                reservation.getThemeTime().getStartTime());
        params.put("quantity", "1"); //1개의 예약은 1개의 수량 고정
        params.put("total_amount", String.valueOf(reservation.getPrice()));
        params.put("vat_amount", "0");
        params.put("tax_free_amount", "0");
        params.put("approval_url", approveURL);
        params.put("cancel_url", cancelURL);
        params.put("fail_url", failURL);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(params, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(KAKAO_PAY_API_URL, entity, Map.class);

        //KakaoPayment table save
        Object tid = response.getBody().get("tid");
        KakaoPayment kakaoPayment = saveKakaoPayment(String.valueOf(tid),reservation);

        //show data
        log.info(KakaoPaymentResponseDto.KakaoPaymentInfo(kakaoPayment));

        return response.getBody();
    }



    /* Utils */

    /**
     * TODO: KakaoPayment 테이블에도 결제정보 저장
     * @param tid kakao가 response 해준 결제정보 id
     * @param reservation
     * @return 저장한 KakaoPayment data
     * @author SEMI
     */
    public KakaoPayment saveKakaoPayment(String tid,Reservation reservation) {

        //우리 테이블에도 저장
        KakaoPayment kakaoPayment = KakaoPayment.builder()
                .cid(cid)
                .tid(tid)
                .reservationId(String.valueOf(reservation.getId()))
                .price(String.valueOf(reservation.getPrice()))
                .themeName(reservation.getTheme().getTitle())
                .userEmail(String.valueOf(reservation.getUser().getEmail()))
                .build();
        paymentRepository.save(kakaoPayment);

        return kakaoPayment;

    }
}