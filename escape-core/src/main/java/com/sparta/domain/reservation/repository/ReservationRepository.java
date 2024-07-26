package com.sparta.domain.reservation.repository;

import com.sparta.domain.reservation.entity.Reservation;
import com.sparta.domain.theme.entity.Theme;
import com.sparta.domain.theme.entity.ThemeTime;
import com.sparta.domain.user.entity.User;
import com.sparta.global.exception.customException.ReservationException;
import com.sparta.global.exception.errorCode.ReservationErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long>, ReservationRepositoryCustom{

    Optional<Reservation> findByIdAndUser(Long reservationId, User user);

    List<Reservation> findAllByTheme(Theme theme);

    default Reservation findByIdAndUserOrElseThrow(Long reservationId, User user) {
        return null;
    }

    default void checkReservation(ThemeTime themeTime){
        if(findByThemeTime(themeTime) != null){
            throw new ReservationException(ReservationErrorCode.RESERVATION_DUPLICATION);
        }
    }

    default Reservation findByIdAndUserAndActive(Long reservationId, User user){
        Reservation reservation = findByIdAndActive(reservationId, user);
        if(reservation == null){
            throw new ReservationException(ReservationErrorCode.RESERVATION_NOT_FOUND);
        }
        return reservation;
    }
}
