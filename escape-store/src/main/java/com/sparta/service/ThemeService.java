package com.sparta.service;

import com.sparta.domain.store.entity.Store;
import com.sparta.domain.store.repository.StoreRepository;
import com.sparta.domain.theme.entity.Theme;
import com.sparta.domain.theme.entity.ThemeStatus;
import com.sparta.domain.theme.repository.ThemeRepository;
import com.sparta.domain.user.entity.User;
import com.sparta.domain.user.entity.UserType;
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
    public ThemeDetailResponseDto createTheme(ThemeCreateRequestDto requestDto, User user) {
        Store store = storeRepository.findByActiveStore(requestDto.getStoreId());

        if(user.getUserType() == UserType.MANAGER) {
            store.checkManager(user);
        }

        Theme theme = Theme.builder()
                .title(requestDto.getTitle())
                .contents(requestDto.getContents())
                .level(requestDto.getLevel())
                .duration(requestDto.getDuration())
                .minPlayer(requestDto.getMinPlayer())
                .maxPlayer(requestDto.getMaxPlayer())
                .price(requestDto.getPrice())
                .themeImage("temp")
                .themeType(requestDto.getThemeType())
                .themeStatus(ThemeStatus.ACTIVE)
                .store(store)
                .build();

        themeRepository.save(theme);
        return new ThemeDetailResponseDto(theme);
    }

    public ThemeGetResponseDto getThemes(Long storeId, User manager) {
        Store findStore = storeRepository.findByActiveStore(storeId);
        findStore.checkManager(manager);

        List<Theme> themeList = themeRepository.findAllByStoreId(storeId);
        return new ThemeGetResponseDto(themeList);
    }

    @Transactional
    public ThemeDetailResponseDto modifyTheme(Long themeId, ThemeModifyRequestDto requestDto, User user) {
        Theme theme = themeRepository.findByIdOrElseThrow(themeId);

        Store store = theme.getStore();
        store.verifyStoreIsActive();

        if(user.getUserType() == UserType.MANAGER) {
            store.checkManager(user);
        }

        theme.updateTheme(
                requestDto.getTitle(),
                requestDto.getContents(),
                requestDto.getLevel(),
                requestDto.getDuration(),
                requestDto.getMinPlayer(),
                requestDto.getMaxPlayer(),
                requestDto.getThemeType(),
                requestDto.getPrice()
        );

        themeRepository.save(theme);
        return new ThemeDetailResponseDto(theme);
    }

    @Transactional
    public void deleteTheme(Long themeId, User user) {
        Theme theme = themeRepository.findByIdOrElseThrow(themeId);

        Store store = theme.getStore();
        store.verifyStoreIsActive();

        if(user.getUserType() == UserType.MANAGER) {
            store.checkManager(user);
        }

        themeRepository.delete(theme);
    }

    @Transactional
    public void changeThemeStatus(Long themeId, User user) {
        Theme theme = themeRepository.findByIdOrElseThrow(themeId);
        Store store = theme.getStore();
        store.verifyStoreIsActive();

        if(user.getUserType() == UserType.MANAGER) {
            store.checkManager(user);
        }

        theme.toggleThemeStatus();
    }
}
