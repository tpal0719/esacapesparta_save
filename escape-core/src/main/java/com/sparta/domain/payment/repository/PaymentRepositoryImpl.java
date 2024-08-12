package com.sparta.domain.payment.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.domain.payment.entity.Payment;
import com.sparta.domain.payment.entity.QPayment;
import com.sparta.domain.reservation.entity.QReservation;
import com.sparta.global.exception.customException.ReservationException;
import com.sparta.global.exception.errorCode.ReservationErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class PaymentRepositoryImpl implements PaymentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Payment findPaymentByReservationThemeTimeId(Long reservationThemeTimeId) {
        QPayment payment = QPayment.payment;
        QReservation reservation = QReservation.reservation;

        Payment result = queryFactory.selectFrom(payment)
                .join(payment.reservation, reservation)
                .where(reservation.themeTime.id.eq(reservationThemeTimeId))
                .fetchOne();

        if (result == null) {
            throw new ReservationException(ReservationErrorCode.RESERVATION_DUPLICATION);
        }

        return result;
    }
}
