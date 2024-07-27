package com.sparta.domain.kakaopayment.repository;

import com.sparta.domain.kakaopayment.entity.KakaoPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<KakaoPayment, Integer> {
}
