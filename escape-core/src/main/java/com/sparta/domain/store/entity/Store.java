package com.sparta.domain.store.entity;

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
public class Store extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String area;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String workHours;

    @Column(nullable = false)
    private String storeImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", nullable = false)
    private User manager;


    @Setter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StoreStatus storeStatus;

    @Builder
    public Store(String name, String address, String phoneNumber, String workHours, String storeImage, User manager, StoreStatus storeStatus) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.workHours = workHours;
        this.storeImage = storeImage;
        this.manager = manager;
        this.storeStatus = storeStatus;
    }
}
