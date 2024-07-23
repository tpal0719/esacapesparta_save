package com.sparta.domain.escapeRoom.repository;

import com.sparta.domain.escapeRoom.entity.EscapeRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EscapeRoomRepository extends JpaRepository<EscapeRoom, Long>, EscapeRoomRepositoryCustom {
}
