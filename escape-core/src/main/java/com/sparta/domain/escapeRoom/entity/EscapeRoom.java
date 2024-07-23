package com.sparta.domain.escapeRoom.entity;

import com.sparta.domain.reservation.entity.Reservation;
import com.sparta.domain.store.entity.Store;
import com.sparta.global.entity.TimeStamped;
import com.sparta.global.exception.customException.EscapeRoomException;
import com.sparta.global.exception.errorCode.EscapeRoomErrorCode;
import jakarta.persistence.*;
import lombok.Builder;
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

    @Column(nullable = false, unique = true)
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

    @Builder
    public EscapeRoom(String title, String contents, Long level, String duration, String theme, String themeImage, Long price, EscapeRoomStatus escapeRoomStatus, Store store) {
        this.title = title;
        this.contents = contents;
        this.level = level;
        this.duration = duration;
        this.theme = theme;
        this.themeImage = themeImage;
        this.price = price;
        this.escapeRoomStatus = escapeRoomStatus;
        this.store = store;
    }

    public void updateEscapeRoom(String title, String contents, Long level, String duration, String theme, Long price) {
        this.title = title;
        this.contents = contents;
        this.level = level;
        this.duration = duration;
        this.theme = theme;
        this.price = price;
    }

    public void deactivateEscapeRoom() {
        this.escapeRoomStatus = EscapeRoomStatus.DEACTIVE;
    }

    public void verifyEscapeRoomIsActive() {
        if(!this.escapeRoomStatus.equals(EscapeRoomStatus.ACTIVE)) {
            throw new EscapeRoomException(EscapeRoomErrorCode.INVALID_ESCAPE_ROOM_STATUS);
        }
    }
}
