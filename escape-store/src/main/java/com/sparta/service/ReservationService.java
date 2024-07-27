package com.sparta.service;

import com.sparta.domain.reservation.entity.Reservation;
import com.sparta.domain.reservation.repository.ReservationRepository;
import com.sparta.domain.theme.entity.Theme;
import com.sparta.domain.theme.repository.ThemeRepository;
import com.sparta.domain.user.entity.User;
import com.sparta.domain.user.entity.UserType;
import com.sparta.dto.response.ReservationsGetResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ThemeRepository themeRepository;

    public ReservationsGetResponseDto getReservations(Long themeId, User user) {
        Theme theme = themeRepository.findThemeOfActiveStore(themeId);

        if(user.getUserType() == UserType.MANAGER) {
            theme.getStore().checkManager(user);
        }

        List<Reservation> reservationList = reservationRepository.findByTheme(theme);
        return new ReservationsGetResponseDto(themeId, reservationList);
    }

    @Transactional
    public void cancelReservation(Long reservationId, User user) {
        Reservation reservation = reservationRepository.findActiveReservation(reservationId);

        if(user.getUserType() == UserType.MANAGER) {
            reservation.getTheme().getStore().checkManager(user);
        }

        // 환불 기능 추가 예정

        reservation.updateReservationStatus();
    }
}
