package com.sparta.domain.review.service;

import com.sparta.domain.review.dto.KafkaReviewRequestDto;
import com.sparta.domain.review.dto.KafkaReviewResponseDto;
import com.sparta.domain.review.dto.ReviewResponseDto;
import com.sparta.domain.review.entity.Review;
import com.sparta.domain.review.repository.ReviewRepository;
import com.sparta.domain.store.repository.StoreRepository;
import com.sparta.domain.theme.entity.Theme;
import com.sparta.domain.theme.repository.ThemeRepository;
import com.sparta.global.KafkaTopic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final KafkaTemplate<String, KafkaReviewRequestDto> kafkaTemplate;
    private final ConcurrentHashMap<String, CompletableFuture<List<ReviewResponseDto>>> responseFutures = new ConcurrentHashMap<>();

    /**
     * 방탈출 카페 테마 리뷰 조회
     *
     * @param storeId 방탈출 카페 id
     * @param themeId 테마 id가 들어있는 dto
     * @return 리뷰 반환
     */
    public List<ReviewResponseDto> getReview(Long storeId, Long themeId) {
        String requestId = UUID.randomUUID().toString();
        CompletableFuture<List<ReviewResponseDto>> future = new CompletableFuture<>();
        responseFutures.put(requestId, future);

        sendReviewRequest(requestId, storeId, themeId);

        // Kafka로 요청을 전송하고 응답을 비동기적으로 기다림
        try {
            return future.get(); // 응답을 기다림
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Failed to get review response", e);
        }
    }

    private void sendReviewRequest(String requestId, Long storeId, Long themeId) {
        KafkaReviewRequestDto reviewRequest = new KafkaReviewRequestDto(requestId, storeId, themeId);
        kafkaTemplate.send(KafkaTopic.REVIEW_REQUEST_TOPIC, reviewRequest);
    }

    @KafkaListener(topics = KafkaTopic.REVIEW_RESPONSE_TOPIC, groupId = "${GROUP_ID}")
    public void handleReviewResponse(KafkaReviewResponseDto reviewResponse) {
        CompletableFuture<List<ReviewResponseDto>> future = responseFutures.remove(reviewResponse.getRequestId());
        if (future != null) {
            future.complete(reviewResponse.getReviewResponses());
        }
    }
}

    /**
     * 방탈출 카페 테마 리뷰 조회
     * @param storeId 방탈출 카페 id
     * @param themeId 테마 id가 들어있는 dto
     * @return 리뷰 반환
     */
//    public List<ReviewResponseDto> getReview(Long storeId, Long themeId) {
//        storeRepository.findByActiveStore(storeId);
//        Theme theme = themeRepository.findByActiveTheme(themeId);
//        List<Review> reviewList = reviewRepository.findByThemeReview(theme);
//
//        return reviewList.stream().map(ReviewResponseDto::new).toList();
//    }

