package com.sparta.domain.escapeRoom.entity;

import com.sparta.domain.reservation.entity.Reservation;
import com.sparta.domain.store.entity.Store;
import com.sparta.global.entity.TimeStamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class EscapeRoom extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private Long level = 0L;

    @Column(nullable = false)
    private String duration;

    @Column(nullable = false)
    private String theme;

    @Column(nullable = false)
    private String themeImage;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EscapeRoomStatus escapeRoomStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @OneToMany(mappedBy = "escapeRoom", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Reservation> reservations;
}
