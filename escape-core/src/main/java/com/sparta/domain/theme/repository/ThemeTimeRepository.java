package com.sparta.domain.theme.repository;

import com.sparta.domain.theme.entity.Theme;
import com.sparta.domain.theme.entity.ThemeTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThemeTimeRepository extends JpaRepository<ThemeTime, Long>, ThemeTimeRepositoryCustom {
    List<ThemeTime> findByTheme(Theme theme);

    List<ThemeTime> findAllByThemeId(Long themeId);
}
