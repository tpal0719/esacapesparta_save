package com.sparta.domain.store.repository;

import com.sparta.domain.store.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepositoryCustom {
    Page<Store> findByName(String name, String area, Pageable pageable);
}
