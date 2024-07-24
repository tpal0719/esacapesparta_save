package com.sparta.domain.store.dto;

import com.sparta.domain.store.entity.Store;
import lombok.Getter;

@Getter
public class StoreResponseDto {
    private String storeImage;
    private String name;
    private String address;

    public StoreResponseDto(Store store){
        this.storeImage = store.getStoreImage();
        this.name = store.getName();
        this.address = store.getAddress();
    }
}
