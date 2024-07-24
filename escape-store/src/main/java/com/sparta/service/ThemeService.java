package com.sparta.service;

import com.sparta.domain.store.entity.Store;
import com.sparta.domain.store.repository.StoreRepository;
import com.sparta.domain.theme.entity.Theme;
import com.sparta.domain.theme.entity.ThemeStatus;
import com.sparta.domain.theme.repository.ThemeRepository;
import com.sparta.domain.user.entity.User;
import com.sparta.dto.request.ThemeCreateRequestDto;
import com.sparta.dto.request.ThemeModifyRequestDto;
import com.sparta.dto.response.ThemeDetailResponseDto;
import com.sparta.dto.response.ThemeGetResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ThemeService {
    private final ThemeRepository themeRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public ThemeDetailResponseDto createTheme(ThemeCreateRequestDto requestDto, User manager) {
        Store store = storeRepository.findByIdOrElseThrow(requestDto.getStoreId());
        store.checkManager(manager);

        Theme theme = Theme.builder()
                .title(requestDto.getTitle())
                .contents(requestDto.getContents())
                .level(requestDto.getLevel())
                .duration(requestDto.getDuration())
                .themeType(requestDto.getThemeType())
                .themeStatus(ThemeStatus.ACTIVE)
                .store(store)
                .build();

        themeRepository.save(theme);
        return new ThemeDetailResponseDto(theme);
    }

    public ThemeGetResponseDto getThemes(Long storeId, User manager) {
        Store findStore = storeRepository.findByIdOrElseThrow(storeId);
        findStore.checkManager(manager);

        List<Theme> themeList = themeRepository.findAllByStoreId(storeId);
        return new ThemeGetResponseDto(themeList);
    }

    @Transactional
    public ThemeDetailResponseDto modifyTheme(Long themeId, ThemeModifyRequestDto requestDto, User manager) {
        Theme theme = themeRepository.findByIdOrElseThrow(themeId);
        theme.verifyThemeIsActive();

        Store store = theme.getStore();
        store.verifyStoreIsActive();
        store.checkManager(manager);

        theme.updateTheme(
                requestDto.getTitle(),
                requestDto.getContents(),
                requestDto.getLevel(),
                requestDto.getDuration(),
                requestDto.getThemeType(),
                requestDto.getPrice()
        );

        themeRepository.save(theme);
        return new ThemeDetailResponseDto(theme);
    }

    @Transactional
    public void deleteTheme(Long themeId, User manager) {
        Theme theme = themeRepository.findByIdOrElseThrow(themeId);
        theme.verifyThemeIsActive();

        Store store = theme.getStore();
        store.verifyStoreIsActive();
        store.checkManager(manager);

        theme.deactivateTheme();
    }
}
