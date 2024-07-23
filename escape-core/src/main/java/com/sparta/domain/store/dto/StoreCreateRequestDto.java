package com.sparta.domain.store.dto;

import com.sparta.domain.store.entity.StoreStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class StoreCreateRequestDto {

    @NotBlank(message = "이름을 적어주세요.")
    private String name;
    @NotBlank(message = "주소를 적어주세요.")
    private String address;
    @NotBlank(message = "전화번호를 적어주세요.")
    private String phoneNumber;
    @NotBlank(message = "오픈 시간을 적어주세요.")
    private String workHours;

    private String storeImage;
    @NotNull
    private Long managerId;
    private StoreStatus storeStatus;

}
