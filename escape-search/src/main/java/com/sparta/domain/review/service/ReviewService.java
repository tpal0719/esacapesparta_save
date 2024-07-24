package com.sparta.domain.review.service;

import com.sparta.domain.review.dto.ReviewResponseDto;
import com.sparta.domain.review.entity.Review;
import com.sparta.domain.review.repository.ReviewRepository;
import com.sparta.domain.store.repository.StoreRepository;
import com.sparta.domain.theme.entity.Theme;
import com.sparta.domain.theme.repository.ThemeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ThemeRepository themeRepository;
    private final StoreRepository storeRepository;

    /**
     * 방탈출 카페 테마 리뷰 조회
     * @param storeId 방탈출 카페 id
     * @param themeId 테마 id가 들어있는 dto
     * @return 리뷰 반환
     */
    public List<ReviewResponseDto> getReview(Long storeId, Long themeId) {
        storeRepository.findByActiveStore(storeId);
        Theme theme = themeRepository.findByActiveTheme(themeId);
        List<Review> reviewList = reviewRepository.findByThemeReview(theme);

        return reviewList.stream().map(ReviewResponseDto::new).toList();
    }
}
