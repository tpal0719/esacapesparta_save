package com.sparta.domain.reservation.service;

import com.sparta.domain.reservation.dto.ReservationListResponseDto;
import com.sparta.domain.reservation.entity.Reservation;
import com.sparta.domain.reservation.repository.ReservationRepository;
import com.sparta.domain.theme.entity.Theme;
import com.sparta.domain.theme.repository.ThemeRepository;
import com.sparta.domain.user.entity.User;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

  private final ReservationRepository reservationRepository;
  private final ThemeRepository themeRepository;

  public ReservationListResponseDto getReservations(Long themeId, User user) {
    Theme theme = themeRepository.findThemeOfActiveStore(themeId);
    theme.getStore().checkManager(user);

    List<Reservation> reservationList = reservationRepository.findByTheme(theme);
    return new ReservationListResponseDto(themeId, reservationList);
  }

  @Transactional
  public void cancelReservation(Long reservationId, User user) {
    Reservation reservation = reservationRepository.findActiveReservation(reservationId);
    reservation.getTheme().getStore().checkManager(user);

    // 환불 기능 추가 예정

    reservation.updateReservationStatus();
  }
}