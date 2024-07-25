package com.sparta.domain.reservation.repository;

import com.sparta.domain.reservation.entity.Reservation;
import com.sparta.domain.user.entity.User;
import com.sparta.global.exception.customException.ReservationException;
import com.sparta.global.exception.errorCode.ReservationErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Optional<Reservation> findByIdAndUser(Long reservationId, User user);

    default Reservation findByIdAndUserOrElseThrow(Long reservationId, User user){
        return findByIdAndUser(reservationId, user).orElseThrow(() ->
                new ReservationException(ReservationErrorCode.RESERVATION_NOT_FOUND));
    }
}
