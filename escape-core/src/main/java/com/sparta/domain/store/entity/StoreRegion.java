package com.sparta.domain.store.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StoreRegion {
    ALL("ALL"),
    SEOUL("SEOUL"),
    BUSAN("BUSAN"),
    DAEGU("DAEGU"),
    INCHEON("INCHEON"),
    GWANGJU("GWANGJU"),
    DAEJEON("DAEJEON"),
    ULSAN("ULSAN"),
    SEJONG("SEJONG"),
    GYEONGGI("GYEONGGI"),
    GANGWON("GANGWON"),
    CHUNGBUK("CHUNGBUK"),
    CHUNGNAM("CHUNGNAM"),
    JEONBUK("JEONBUK"),
    JEONNAM("JEONNAM"),
    GYEONGBUK("GYEONGBUK"),
    GYEONGNAM("GYEONGNAM"),
    JEJU("JEJU");

    private final String koreanName;

    @Override
    public String toString() {
        return koreanName;
    }
}
