package com.sparta.domain.escapeRoom.repository;

import com.sparta.domain.escapeRoom.entity.Theme;
import com.sparta.domain.store.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface EscapeRoomRepositoryCustom {
    Page<Theme> findByStore(Store store, Pageable pageable);
}
