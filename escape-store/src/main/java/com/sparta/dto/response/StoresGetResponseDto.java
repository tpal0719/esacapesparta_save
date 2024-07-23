package com.sparta.dto.response;

import com.sparta.domain.store.entity.Store;
import lombok.Getter;

import java.util.List;

@Getter
public class StoresGetResponseDto {
    private int totalStore;
    private List<StoreDetailResponseDto> storeDetailResponseDtoList;

    public StoresGetResponseDto(List<Store> storeList) {
        this.totalStore = storeList.size();
        this.storeDetailResponseDtoList = storeList.stream().map(StoreDetailResponseDto::new).toList();
    }
}
