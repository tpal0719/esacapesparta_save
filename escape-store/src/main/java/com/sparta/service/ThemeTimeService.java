package com.sparta.service;

import com.sparta.domain.theme.entity.Theme;
import com.sparta.domain.theme.entity.ThemeTime;
import com.sparta.domain.theme.repository.ThemeRepository;
import com.sparta.domain.theme.repository.ThemeTimeRepository;
import com.sparta.domain.user.entity.User;
import com.sparta.domain.user.entity.UserType;
import com.sparta.dto.request.ThemeTimeCreateRequestDto;
import com.sparta.dto.request.ThemeTimeModifyRequestDto;
import com.sparta.dto.response.ThemeTimeDetailResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.sparta.global.util.LocalDateTimeUtil.*;

@Service
@RequiredArgsConstructor
public class ThemeTimeService {
    private final ThemeTimeRepository themeTimeRepository;
    private final ThemeRepository themeRepository;

    @Transactional
    public ThemeTimeDetailResponseDto createThemeTime(Long themeId, ThemeTimeCreateRequestDto requestDto, User user) {
        Theme theme = themeRepository.findThemeOfActiveStore(themeId);

        if(user.getUserType() == UserType.MANAGER) {
            theme.getStore().checkManager(user);
        }

        LocalDateTime startTime = parseDateTimeStringToLocalDateTime(requestDto.getStartTime());
        LocalDateTime endTime = calculateEndTime(startTime, theme.getDuration());

        ThemeTime themeTime = ThemeTime.builder()
                .startTime(startTime)
                .endTime(endTime)
                .theme(theme)
                .build();

        themeTimeRepository.save(themeTime);
        return new ThemeTimeDetailResponseDto(themeTime);
    }

    public List<ThemeTimeDetailResponseDto> getThemeTimes(Long themeId, String date, User user) {
        Theme theme = themeRepository.findThemeOfActiveStore(themeId);

//        Theme theme = themeRepository.findByIdOrElseThrow(themeId);
//        Store store = theme.getStore();
//        store.verifyStoreIsActive();

        if(user.getUserType() == UserType.MANAGER) {
            theme.getStore().checkManager(user);
        }

        List<ThemeTime> themeTimes;
        if(date == null) {
            // 매니저는 비활성화된 Theme의 예약 시간대도 조회 가능
            themeTimes = themeTimeRepository.findAllByThemeId(themeId);
        } else {
            LocalDate searchDate = parseDateStringToLocalDate(date);
            themeTimes = themeTimeRepository.findThemeTimesByDate(themeId, searchDate);
        }

        return themeTimes.stream().map(ThemeTimeDetailResponseDto::new).toList();
    }

    @Transactional
    public ThemeTimeDetailResponseDto modifyThemeTime(Long themeTimeId, ThemeTimeModifyRequestDto requestDto, User user) {
        ThemeTime themeTime = themeTimeRepository.findThemeTimeOfActiveStore(themeTimeId);

        if(user.getUserType() == UserType.MANAGER) {
            themeTime.getTheme().getStore().checkManager(user);
        }

        LocalDateTime startTime = parseDateTimeStringToLocalDateTime(requestDto.getStartTime());
        LocalDateTime endTime = calculateEndTime(startTime, themeTime.getTheme().getDuration());

        themeTime.updateThemeTime(startTime, endTime);
        themeTimeRepository.save(themeTime);

        return new ThemeTimeDetailResponseDto(themeTime);
    }

    @Transactional
    public void deleteThemeTime(Long themeTimeId, User user) {
        ThemeTime themeTime = themeTimeRepository.findThemeTimeOfActiveStore(themeTimeId);

        if(user.getUserType() == UserType.MANAGER) {
            themeTime.getTheme().getStore().checkManager(user);
        }

        themeTimeRepository.delete(themeTime);
    }

}
