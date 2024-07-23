package com.sparta.domain.store.dto;

import com.sparta.domain.store.entity.StoreStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class StoreCreateRequestDto {

    @NotBlank
    private String name;
    @NotBlank
    private String address;
    @NotBlank
    private String phoneNumber;
    @NotBlank
    private String workHours;
    @NotBlank
    private String storeImage;
    @NotBlank
    private StoreStatus storeStatus;

}
