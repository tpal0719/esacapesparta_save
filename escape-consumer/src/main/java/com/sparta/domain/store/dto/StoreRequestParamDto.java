package com.sparta.domain.store.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoreRequestParamDto {
    private String keyWord = "";
    private String area = "";
    private Integer pageNum = 1;
    private Boolean isDesc = false;

    @Builder
    public StoreRequestParamDto(String keyWord, String area, Integer pageNum, Boolean isDesc) {
        this.keyWord = keyWord;
        this.area = area;
        this.pageNum = pageNum;
        this.isDesc = isDesc;
    }
}
