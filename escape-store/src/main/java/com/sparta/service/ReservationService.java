package com.sparta.service;

import com.sparta.domain.reservation.entity.Reservation;
import com.sparta.domain.reservation.repository.ReservationRepository;
import com.sparta.domain.theme.entity.Theme;
import com.sparta.domain.theme.repository.ThemeRepository;
import com.sparta.domain.user.entity.User;
import com.sparta.domain.user.entity.UserType;
import com.sparta.dto.response.ReservationsGetResponseDto;
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

        List<Reservation> reservationList = reservationRepository.findAllByTheme(theme);
        return new ReservationsGetResponseDto(themeId, reservationList);
    }

}
