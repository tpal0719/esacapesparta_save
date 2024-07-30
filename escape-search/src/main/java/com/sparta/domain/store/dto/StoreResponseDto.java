package com.sparta.domain.store.dto;

import com.sparta.domain.store.entity.Store;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoreResponseDto {
    private Long storeId;
    private String storeImage;
    private String name;
    private String address;

    public StoreResponseDto(Store store){
        this.storeId = store.getId();
        this.storeImage = store.getStoreImage();
        this.name = store.getName();
        this.address = store.getAddress();
    }
}
