package com.sparta.domain.payment.repository;

import com.sparta.domain.payment.entity.Payment;
import com.sparta.global.exception.customException.ReservationException;
import com.sparta.global.exception.errorCode.ReservationErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    default Payment findByReservationThemeTimeIdOrElse(Long reservationThemeTimeId) {
        return findByReservationThemeTimeId(reservationThemeTimeId).orElseThrow(() ->
                new ReservationException(ReservationErrorCode.RESERVATION_DUPLICATION)
        );
    }

    Optional<Payment> findByReservationThemeTimeId(Long reservationThemeTimeId);
}
