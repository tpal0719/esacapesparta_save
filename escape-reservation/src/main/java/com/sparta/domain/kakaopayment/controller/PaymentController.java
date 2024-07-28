package com.sparta.domain.kakaopayment.controller;


import com.sparta.domain.kakaopayment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/reservationId/{reservationId}")
    public String preparePayment(Long reservationId,
                                 Model model) {
        Map<String, Object> response = paymentService.preparePayment(reservationId);
        model.addAttribute("nextRedirectPcUrl", response.get("next_redirect_pc_url"));
        return "redirect:" + response.get("next_redirect_pc_url");
    }

    @GetMapping("/kakaoPaySuccess")
    public String kakaoPaySuccess(@RequestParam("pg_token") String pgToken, Model model) {
        model.addAttribute("message", "결제 성공");
        return "paymentResult";
    }

    @GetMapping("/kakaoPayCancel")
    public String kakaoPayCancel(Model model) {
        model.addAttribute("message", "결제 취소");
        return "paymentResult";
    }

    @GetMapping("/kakaoPayFail")
    public String kakaoPayFail(Model model) {
        model.addAttribute("message", "결제 실패");
        return "paymentResult";
    }
}