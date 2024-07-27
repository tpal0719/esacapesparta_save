package com.sparta.domain.kakaopayment.controller;

import com.sparta.domain.kakaopayment.service.KakaoPayService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {


    private final KakaoPayService kakaoPayService;

    @GetMapping
    public String paymentForm() {
        return "paymentForm";
    }

    @PostMapping("/prepare/{reservationId}")
    public String preparePayment(@PathVariable Long reservationId,
                                 Model model) {
        Map<String, Object> response = kakaoPayService.preparePayment(reservationId);
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