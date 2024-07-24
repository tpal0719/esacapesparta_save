package com.sparta.domain.theme.entity;

import com.sparta.domain.reservation.entity.Reservation;
import com.sparta.domain.store.entity.Store;
import com.sparta.global.entity.TimeStamped;
import com.sparta.global.exception.customException.ThemeException;
import com.sparta.global.exception.errorCode.ThemeErrorCode;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Theme extends TimeStamped {
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
    @Enumerated(EnumType.STRING)
    private ThemeType themeType;

    @Column(nullable = false)
    private String themeImage;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ThemeStatus themeStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @OneToMany(mappedBy = "theme", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Reservation> reservations;

    @Builder
    public Theme(String title, String contents, Long level, String duration, ThemeType themeType, String themeImage, Long price, ThemeStatus themeStatus, Store store) {
        this.title = title;
        this.contents = contents;
        this.level = level;
        this.duration = duration;
        this.themeType = themeType;
        this.themeImage = themeImage;
        this.price = price;
        this.themeStatus = themeStatus;
        this.store = store;
    }

    public void updateTheme(String title, String contents, Long level, String duration, ThemeType themeType, Long price) {
        this.title = title;
        this.contents = contents;
        this.level = level;
        this.duration = duration;
        this.themeType = themeType;
        this.price = price;
    }

    public void deactivateTheme() {
        this.themeStatus = ThemeStatus.DEACTIVE;
    }

    public void verifyThemeIsActive() {
        if(!this.themeStatus.equals(ThemeStatus.ACTIVE)) {
            throw new ThemeException(ThemeErrorCode.INVALID_THEME_STATUS);
        }
    }
}
