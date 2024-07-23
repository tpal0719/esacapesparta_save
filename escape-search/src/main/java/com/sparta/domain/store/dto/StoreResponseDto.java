package com.sparta.domain.store.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class StoreResponseDto {
    private String storeImage;
    private String name;
    private String address;

    @Builder
    public StoreResponseDto(String storeImage, String name, String address){
        this.storeImage = storeImage;
        this.name = name;
        this.address = address;
    }
}
