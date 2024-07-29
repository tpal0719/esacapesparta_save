package com.sparta.domain.theme.service;

import com.sparta.domain.s3.S3Uploader;
import com.sparta.domain.store.entity.Store;
import com.sparta.domain.store.repository.StoreRepository;
import com.sparta.domain.theme.dto.request.ThemeCreateRequestDto;
import com.sparta.domain.theme.dto.request.ThemeModifyRequestDto;
import com.sparta.domain.theme.dto.response.ThemeDetailResponseDto;
import com.sparta.domain.theme.dto.response.ThemeGetResponseDto;
import com.sparta.domain.theme.entity.Theme;
import com.sparta.domain.theme.entity.ThemeStatus;
import com.sparta.domain.theme.repository.ThemeRepository;
import com.sparta.domain.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ThemeService {
    private final ThemeRepository themeRepository;
    private final StoreRepository storeRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public ThemeDetailResponseDto createTheme(MultipartFile file, ThemeCreateRequestDto requestDto, User user) {
        Store store = storeRepository.findByActiveStore(requestDto.getStoreId());
        store.checkManager(user);

        Theme theme = Theme.builder()
                .title(requestDto.getTitle())
                .contents(requestDto.getContents())
                .level(requestDto.getLevel())
                .duration(requestDto.getDuration())
                .minPlayer(requestDto.getMinPlayer())
                .maxPlayer(requestDto.getMaxPlayer())
                .price(requestDto.getPrice())
                .themeType(requestDto.getThemeType())
                .themeStatus(ThemeStatus.ACTIVE)
                .store(store)
                .build();

        themeRepository.save(theme);

        String themeImage = s3Uploader.uploadThemeImage(file, store.getId(),theme.getId());
        theme.updateThemeImage(themeImage);

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
        Theme theme = themeRepository.findThemeOfActiveStore(themeId);
        theme.getStore().checkManager(user);

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
    public String modifyThemeImage(Long themeId, MultipartFile file, User user) {
        Theme theme = themeRepository.findThemeOfActiveStore(themeId);
        theme.getStore().checkManager(user);

        s3Uploader.deleteFileFromS3(theme.getThemeImage());
        String themeImage = s3Uploader.uploadThemeImage(file, theme.getStore().getId(), themeId);
        theme.updateThemeImage(themeImage);

        return themeImage;
    }

    @Transactional
    public void deleteThemeImage(Long themeId, User user) {
        Theme theme = themeRepository.findThemeOfActiveStore(themeId);
        theme.getStore().checkManager(user);

        s3Uploader.deleteFileFromS3(theme.getThemeImage());
        theme.deleteThemeImage();
    }

    @Transactional
    public void deleteTheme(Long themeId, User user) {
        Theme theme = themeRepository.findThemeOfActiveStore(themeId);
        theme.getStore().checkManager(user);

        themeRepository.delete(theme);
    }

    @Transactional
    public void changeThemeStatus(Long themeId, User user) {
        Theme theme = themeRepository.findThemeOfActiveStore(themeId);
        theme.getStore().checkManager(user);

        theme.toggleThemeStatus();
    }
}

