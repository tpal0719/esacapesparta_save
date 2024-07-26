package com.sparta.kakaopayment.controller;

import org.springframework.ui.Model;
import com.sparta.kakaopayment.service.KakaoPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private KakaoPayService kakaoPayService;

    @GetMapping
    public String paymentForm() {
        return "paymentForm";
    }

    @PostMapping("/prepare/{reservationId}")
    public String preparePayment(@RequestParam Long reservationId,
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