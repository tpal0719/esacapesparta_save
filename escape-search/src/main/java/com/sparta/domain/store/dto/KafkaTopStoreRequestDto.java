package com.sparta.domain.store.dto;

import com.sparta.domain.store.entity.StoreRegion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KafkaTopStoreRequestDto {
    private String requestId;
}
