package com.sparta.domain.theme.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.domain.theme.entity.QThemeTime;
import com.sparta.domain.theme.entity.ThemeTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ThemeTimeRepositoryImpl implements ThemeTimeRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ThemeTime> findThemeTimesByDate(Long themeId, LocalDate date) {
        QThemeTime themeTime = QThemeTime.themeTime;

        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        return jpaQueryFactory.selectFrom(themeTime)
                .where(themeTime.startTime.between(startOfDay, endOfDay))
                .fetch();
    }
}
