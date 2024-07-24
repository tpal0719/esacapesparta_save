package com.sparta.domain.theme.service;

import com.sparta.domain.store.entity.Store;
import com.sparta.domain.store.repository.StoreRepository;
import com.sparta.domain.theme.dto.ThemeInfoResponseDto;
import com.sparta.domain.theme.dto.ThemeResponseDto;
import com.sparta.domain.theme.dto.ThemeTimeResponseDto;
import com.sparta.domain.theme.entity.Theme;
import com.sparta.domain.theme.entity.ThemeTime;
import com.sparta.domain.theme.repository.ThemeRepository;
import com.sparta.domain.theme.repository.ThemeTimeRepository;
import com.sparta.global.util.PageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ThemeService {

    private final ThemeRepository themeRepository;
    private final StoreRepository storeRepository;
    private final ThemeTimeRepository themeTimeRepository;

    /**
     * 방탈출 카페 테마 전체 조회
     * @param storeId 방탈출 카페 id
     * @param pageNum 페이지 번호
     * @param pageSize 페이지에 담는 데이터 수
     * @param isDesc 오름차순, 내림차순 정렬 기준
     * @param sort 속성별 정렬 기준
     * @return EscapeRoom 리스트
     */
    public Page<ThemeResponseDto> getTheme(Long storeId, int pageNum, int pageSize, boolean isDesc, String sort) {
        Store store = storeRepository.findByIdOrElseThrow(storeId);

        Pageable pageable = PageUtil.createPageable(pageNum, pageSize, isDesc, sort);
        Page<Theme> themes = themeRepository.findByStore(store, pageable);

        return themes.map(ThemeResponseDto::new);
    }

    /**
     * 방탈출 카페 테마 상세 조회
     * @param storeId 해당 카페 id
     * @param themeId 해당 카페의 테마 id
     * @return theme 정보 반환
     */
    public ThemeInfoResponseDto getThemeInfo(Long storeId, Long themeId) {
        storeRepository.findByActiveStore(storeId);
        Theme theme = themeRepository.findByActiveTheme(themeId);

        return new ThemeInfoResponseDto(theme);
    }

    /**
     * 방탈출 카페 테마 시간 조회
     * @param storeId 검색할 테마의 스토어 id
     * @param themeId 해당 카페의 테마 id
     * @return theme 시간 반환
     */
    public List<ThemeTimeResponseDto> getThemeTime(Long storeId, Long themeId) {
        storeRepository.findByActiveStore(storeId);
        Theme theme = themeRepository.findByActiveTheme(themeId);
        List<ThemeTime> themeTimeList = themeTimeRepository.findByTheme(theme);

        return themeTimeList.stream().map(ThemeTimeResponseDto::new).toList();
    }
}
