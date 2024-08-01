package com.sparta.domain.kakaopayment.controller;


import com.sparta.domain.kakaopayment.service.PaymentService;
import com.sparta.domain.reservation.entity.Reservation;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/reservations/{reservationId}")
    public String preparePayment(@PathVariable Long reservationId,
                                 Model model) {
        Map<String, Object> response = paymentService.preparePayment(reservationId);
        model.addAttribute("nextRedirectPcUrl", response.get("next_redirect_pc_url"));
        return "" + response.get("next_redirect_pc_url");
    }

    @GetMapping("/kakaoPaySuccess")
    public ResponseEntity<String> kakaoPaySuccess(@RequestParam("pg_token") String pgToken, Model model) {
        return ResponseEntity.ok("결제 성공");
    }

    @GetMapping("/kakaoPayCancel")
    public ResponseEntity<String> kakaoPayCancel() {
        return ResponseEntity.ok("결제 취소");
    }

    @GetMapping("/kakaoPayFail")
    public ResponseEntity<String> kakaoPayFail() {
        return ResponseEntity.ok("결제 실패");
    }

}