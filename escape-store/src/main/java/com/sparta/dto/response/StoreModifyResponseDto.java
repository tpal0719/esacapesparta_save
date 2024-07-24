package com.sparta.dto.response;

import com.sparta.domain.store.entity.Store;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class StoreModifyResponseDto {
    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
    private String workHours;
    private String storeImage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public StoreModifyResponseDto(Store store) {
        this.id = store.getId();
        this.name = store.getName();
        this.address = store.getAddress();
        this.phoneNumber = store.getPhoneNumber();
        this.workHours = store.getWorkHours();
        this.storeImage = store.getStoreImage();
        this.createdAt = store.getCreatedAt();
        this.updatedAt = store.getUpdatedAt();
    }
}