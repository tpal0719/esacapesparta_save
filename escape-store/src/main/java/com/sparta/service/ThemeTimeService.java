package com.sparta.service;

import com.sparta.domain.store.entity.Store;
import com.sparta.domain.theme.entity.Theme;
import com.sparta.domain.theme.entity.ThemeTime;
import com.sparta.domain.theme.repository.ThemeRepository;
import com.sparta.domain.theme.repository.ThemeTimeRepository;
import com.sparta.domain.user.entity.User;
import com.sparta.domain.user.entity.UserType;
import com.sparta.dto.request.ThemeTimeCreateRequestDto;
import com.sparta.dto.response.ThemeTimeDetailResponseDto;
import com.sparta.global.exception.customException.ThemeTimeException;
import com.sparta.global.exception.errorCode.ThemeTimeErrorCode;
import com.sparta.global.util.LocalDateTimeUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ThemeTimeService {
    private final ThemeTimeRepository themeTimeRepository;
    private final ThemeRepository themeRepository;

    @Transactional
    public ThemeTimeDetailResponseDto createThemeTime(Long themeId, ThemeTimeCreateRequestDto requestDto, User user) {
        Theme theme = themeRepository.findByIdOrElseThrow(themeId);
        Store store = theme.getStore();
        store.verifyStoreIsActive();

        if(user.getUserType() == UserType.MANAGER) {
            store.checkManager(user);
        }

        LocalDateTime startTime = LocalDateTimeUtils.parseStringToLocalDateTime(requestDto.getStartTime());
        LocalDateTime endTime = LocalDateTimeUtils.parseStringToLocalDateTime(requestDto.getEndTime());

        checkValidStartTimeAndEndTime(startTime, endTime);

        ThemeTime themeTime = ThemeTime.builder()
                .startTime(startTime)
                .endTime(endTime)
                .theme(theme)
                .build();

        themeTimeRepository.save(themeTime);
        return new ThemeTimeDetailResponseDto(themeTime);
    }

    private void checkValidStartTimeAndEndTime(LocalDateTime startTime, LocalDateTime endTime) {
        if(startTime.isAfter(endTime)) {
            throw new ThemeTimeException(ThemeTimeErrorCode.INVALID_START_AND_END_TIME);
        }
    }

}
