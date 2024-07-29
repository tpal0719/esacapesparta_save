package com.sparta.domain.reservation.service;

import com.sparta.domain.reservation.dto.ReservationsGetResponseDto;
import com.sparta.domain.reservation.entity.Reservation;
import com.sparta.domain.reservation.repository.ReservationRepository;
import com.sparta.domain.theme.entity.Theme;
import com.sparta.domain.theme.repository.ThemeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationAdminService {
    private final ReservationRepository reservationRepository;
    private final ThemeRepository themeRepository;

    public ReservationsGetResponseDto getReservations(Long themeId) {
        Theme theme = themeRepository.findThemeOfActiveStore(themeId);

        List<Reservation> reservationList = reservationRepository.findByTheme(theme);
        return new ReservationsGetResponseDto(themeId, reservationList);
    }

    @Transactional
    public void cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findActiveReservation(reservationId);

        // 환불 기능 추가 예정

        reservation.updateReservationStatus();
    }
}