package com.sparta.dto.response;

import com.sparta.domain.store.entity.Store;
import lombok.Getter;

@Getter
public class StoreDetailResponseDto {
    private String name;
    private String address;
    private String phoneNumber;
    private String workHours;
    private String storeImage;
    private Long managerId;
    private String managerName;

    public StoreDetailResponseDto(Store store) {
        this.name = store.getName();
        this.address = store.getAddress();
        this.phoneNumber = store.getPhoneNumber();
        this.workHours = store.getWorkHours();
        this.storeImage = store.getStoreImage();
        this.managerId = store.getManager().getId();
        this.managerName = store.getManager().getName();
    }

//    public static StoreDetailResponseDto of(Store store) {
//        return StoreDetailResponseDto.builder()
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
