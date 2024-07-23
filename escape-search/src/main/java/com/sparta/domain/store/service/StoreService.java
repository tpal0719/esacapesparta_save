package com.sparta.domain.store.service;

import com.sparta.domain.store.dto.StoreRequestParamDto;
import com.sparta.domain.store.dto.StoreResponseDto;
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

    public Page<StoreResponseDto> getStores(StoreRequestParamDto storeRequestParamDto) {

        Pageable pageable = PageUtil.createPageable(storeRequestParamDto.getPageNum(),
                storeRequestParamDto.getPageSize(),
                storeRequestParamDto.getIsDesc(),
                storeRequestParamDto.getSort());


        return null;
    }
}
