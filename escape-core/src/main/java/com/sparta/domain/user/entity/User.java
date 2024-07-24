package com.sparta.domain.user.entity;

import com.sparta.domain.reaction.entity.Reaction;
import com.sparta.domain.reservation.entity.Reservation;
import com.sparta.global.entity.TimeStamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long point = 0L;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OAuthProvider OAuthProvider;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Reservation> reservations;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Reaction> reactions;


    public User(String name, String email, String password,OAuthProvider oAuthProvider,UserType userType, UserStatus userStatus) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.OAuthProvider = oAuthProvider;
        this.userType = userType;
        this.userStatus = userStatus;
    }
}
