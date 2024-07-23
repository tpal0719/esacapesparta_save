package com.sparta.domain.store.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoreRequestParamDto {
    private String keyWord = "";
    private String area = "";
    private String sort = "name";
    private int pageNum = 1;
    private int pageSize = 10;
    private Boolean isDesc = false;

    @Builder
    public StoreRequestParamDto(String keyWord, String area, String sort, int pageNum, int pageSize, Boolean isDesc) {
        this.keyWord = keyWord;
        this.area = area;
        this.sort = sort;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.isDesc = isDesc;
    }
}
