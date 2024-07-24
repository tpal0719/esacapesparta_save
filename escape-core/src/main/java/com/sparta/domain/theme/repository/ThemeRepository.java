package com.sparta.domain.theme.repository;

import com.sparta.domain.theme.entity.Theme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThemeRepository extends JpaRepository<Theme, Long> {
}
