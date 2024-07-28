package com.sparta.store.dto;

import com.sparta.domain.store.entity.StoreStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
public class StoreResponseDto {
    private Long storeId;
    private String name;
    private String address;
    private String phoneNumber;
    private String workHours;
    private String storeImage;
    private StoreStatus storeStatus;

    @Builder
    public StoreResponseDto(com.sparta.domain.store.entity.Store store) {
        this.storeId = store.getId();
        this.name = store.getName();
        this.address = store.getAddress();
        this.phoneNumber = store.getPhoneNumber();
        this.workHours = store.getWorkHours();
        this.storeImage = store.getStoreImage();
        this.storeStatus = store.getStoreStatus();
    }
}

