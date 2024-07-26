package com.sparta.kakaopayment.repository;

import com.sparta.kakaopayment.entity.KakaoPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<KakaoPayment, Integer> {
}
