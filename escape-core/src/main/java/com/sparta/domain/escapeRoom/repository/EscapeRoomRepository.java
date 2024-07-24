package com.sparta.domain.escapeRoom.repository;

import com.sparta.domain.escapeRoom.entity.Theme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EscapeRoomRepository extends JpaRepository<Theme, Long>, EscapeRoomRepositoryCustom {
}
