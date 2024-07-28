package com.sparta.domain.reservation.entity;

import com.sparta.domain.theme.entity.Theme;
import com.sparta.domain.theme.entity.ThemeTime;
import com.sparta.domain.user.entity.User;
import com.sparta.global.entity.TimeStamped;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class Reservation extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // payment 추가
    @Setter
    private String tid; //결제완료시 코드
    @Setter
    private String cid; //가맹점 코드

    @Column(nullable = false)
    private Integer player; //플레이 인원

    @Column(nullable = false)
    private Long price;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatus reservationStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theme_id", nullable = false)
    private Theme theme;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theme_time_id", nullable = false)
    private ThemeTime themeTime;

    @Builder
    public Reservation(Integer player, Long price, PaymentStatus paymentStatus, ReservationStatus reservationStatus, User user, Theme theme, ThemeTime themeTime){
        this.player = player;
        this.price = price;
        this.paymentStatus = paymentStatus;
        this.reservationStatus = reservationStatus;
        this.user = user;
        this.theme = theme;
        this.themeTime = themeTime;
    }

    public void updateReservationStatus(){
        this.reservationStatus = ReservationStatus.DEACTIVE;
    }
}
