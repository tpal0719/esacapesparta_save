package com.sparta.domain.reservation.service;

import com.sparta.domain.kakaopayment.service.KakaoPayService;
import com.sparta.domain.reservation.dto.CreateReservationRequestDto;
import com.sparta.domain.reservation.dto.CreateReservationResponseDto;
import com.sparta.domain.reservation.dto.GetReservationResponseDto;
import com.sparta.domain.reservation.entity.Reservation;
import com.sparta.domain.reservation.entity.ReservationStatus;
import com.sparta.domain.reservation.repository.ReservationRepository;
import com.sparta.domain.theme.entity.ThemeTime;
import com.sparta.domain.theme.repository.ThemeTimeRepository;
import com.sparta.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ThemeTimeRepository themeTimeRepository;
    private final KakaoPayService kakaoPayService;


    /**
     * 예약 생성
     * @param requestDto 예약 생성에 필요한 데이터
     * @param user 로그인 유저
     * @return 예약
     */
    @Transactional
    public CreateReservationResponseDto createReservation(CreateReservationRequestDto requestDto, User user) {
        ThemeTime themeTime = themeTimeRepository.checkStoreAndThemeActive(requestDto.getThemeTimeId());
        reservationRepository.checkReservation(themeTime);

        Reservation reservation = Reservation.builder()
                .player(requestDto.getPlayer())
                .price(requestDto.getPrice())
                .paymentStatus(requestDto.getPaymentStatus())
                .reservationStatus(ReservationStatus.DEACTIVE)
                .user(user)
                .theme(themeTime.getTheme())
                .themeTime(themeTime)
                .build();

        reservationRepository.save(reservation);
        kakaoPayService.preparePayment(reservation.getId());

        return new CreateReservationResponseDto(reservation);
    }

    /**
     * 예약 취소
     * @param reservationId 취소할 에약 id
     * @param user 로그인 유저
     */
    @Transactional
    public void deleteReservation(Long reservationId, User user) {
        Reservation reservation = reservationRepository.findByIdAndUserAndActive(reservationId, user);
        reservation.updateReservationStatus();
        //환불 하는 기능 추가 해야됨
    }

    /**
     * 예약 내역 조회
     *
     * @param user 로그인 유저
     * @return 예약 내역
     */
    public List<GetReservationResponseDto> getReservations(User user) {
        List<Reservation> reservationList = reservationRepository.findByUser(user);

        return reservationList.stream().map(GetReservationResponseDto::new).toList();
    }
}
