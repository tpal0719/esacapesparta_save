package com.sparta.domain.store.repository;

import com.sparta.domain.follow.entity.Follow;
import com.sparta.domain.store.entity.Store;
import com.sparta.domain.store.entity.StoreRegion;
import com.sparta.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepositoryCustom {
    Page<Store> findByName(String name, StoreRegion storeRegion, Pageable pageable);
    Store findByActiveStore(Long storeId);
}
