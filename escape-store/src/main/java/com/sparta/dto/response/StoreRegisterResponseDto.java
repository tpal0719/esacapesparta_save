package com.sparta.dto.response;

import com.sparta.domain.store.entity.Store;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class StoreRegisterResponseDto {
    private Long storeId;
    private String name;
    private String address;
    private String phoneNumber;
    private String workHours;
    private String storeImage;
    private Long managerId;
    private String managerName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public StoreRegisterResponseDto(Store store) {
        this.storeId = store.getId();
        this.name = store.getName();
        this.address = store.getAddress();
        this.phoneNumber = store.getPhoneNumber();
        this.workHours = store.getWorkHours();
        this.storeImage = store.getStoreImage();
        this.managerId = store.getManager().getId();
        this.managerName = store.getManager().getName();
        this.createdAt = store.getCreatedAt();
        this.updatedAt = store.getUpdatedAt();
    }

//    public static StoreCreateResponseDto of(Store store) {
//        return StoreCreateResponseDto.builder()
//                .id(store.getId())
//                .name(store.getName())
//                .address(store.getAddress())
//                .phoneNumber(store.getPhoneNumber())
//                .workHours(store.getWorkHours())
//                .storeImage(store.getStoreImage())
//                .managerId(store.getManager().getId())
//                .managerName(store.getManager().getName())
//                .build();
//    }
}
