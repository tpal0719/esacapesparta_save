package com.sparta.domain.escapeRoom.repository;

import com.sparta.domain.escapeRoom.entity.Theme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThemeRepository extends JpaRepository<Theme, Long>, ThemeRepositoryCustom {
}
