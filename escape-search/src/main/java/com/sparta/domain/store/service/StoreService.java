package com.sparta.domain.store.service;

import com.sparta.domain.store.dto.StoreResponseDto;
import com.sparta.domain.store.entity.Store;
import com.sparta.domain.store.repository.StoreRepository;
import com.sparta.global.util.PageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    /**
     * 방탈출 카페 조회
     * @param pageNum 페이지 번호
     * @param pageSize 페이지에 담는 데이터 수
     * @param isDesc 오름차순, 내림차순 정렬 기준
     * @param keyWord 검색 키워드
     * @param area 카페 지역
     * @param sort 속성별 정렬 기준
     * @return Store 리스트
     */
    public Page<StoreResponseDto> getStores(int pageNum, int pageSize, boolean isDesc,
                                            String keyWord, String area, String sort) {

        Pageable pageable = PageUtil.createPageable(pageNum, pageSize, isDesc, sort);
        Page<Store> stores = storeRepository.findByName(keyWord, area, pageable);
        return stores.map(StoreResponseDto::new);
    }
}
