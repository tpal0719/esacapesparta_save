package com.sparta.domain.review.controller;

import com.sparta.domain.review.dto.*;
import com.sparta.domain.review.service.ReviewService;
import com.sparta.global.response.ResponseMessage;
import com.sparta.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * 테마 리뷰 수정
     * @param createReviewRequestDto 작성할 리뷰의 데이터 값
     * @param userDetails 로그인 유저
     * @return status.code, message, 작성한 리뷰
     */
    @PostMapping
    public ResponseEntity<ResponseMessage<CreateReviewResponseDto>> createReview(
            @Valid @RequestBody CreateReviewRequestDto createReviewRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails){

        CreateReviewResponseDto createReviewResponseDto = reviewService.createReview(createReviewRequestDto, userDetails.getUser());

        ResponseMessage<CreateReviewResponseDto> responseMessage = ResponseMessage.<CreateReviewResponseDto>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("리뷰 등록 성공!")
                .data(createReviewResponseDto)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
    }

    /**
     * 테마 리뷰 수정
     * @param reviewId 수정할 리뷰의 id
     * @param updateReviewRequestDto 수정할 리뷰의 데이터 값
     * @param userDetails 로그인 유저
     * @return status.code, message, 수정한 리뷰
     */
    @PutMapping("/{reviewId}")
    public ResponseEntity<ResponseMessage<UpdateReviewResponseDto>> updateReview(
            @PathVariable Long reviewId,
            @Valid @RequestBody UpdateReviewRequestDto updateReviewRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails){

        UpdateReviewResponseDto updateReviewResponseDto = reviewService.updateReview(reviewId, updateReviewRequestDto,
                                                                                                userDetails.getUser());

        ResponseMessage<UpdateReviewResponseDto> responseMessage = ResponseMessage.<UpdateReviewResponseDto>builder()
                .statusCode(HttpStatus.OK.value())
                .message("리뷰 수정 성공!")
                .data(updateReviewResponseDto)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    /**
     * 테마 리뷰 삭제
     * @param reviewId 수정할 리뷰의 id
     * @param userDetails 로그인 유저
     * @return status.code, message
     */
    @DeleteMapping("/{reviewId}")
        public ResponseEntity<ResponseMessage<Void>> deleteReview(
                @PathVariable Long reviewId,
                @AuthenticationPrincipal UserDetailsImpl userDetails){

        reviewService.deleteReview(reviewId, userDetails.getUser());

        ResponseMessage<Void> responseMessage = ResponseMessage.<Void>builder()
                .statusCode(HttpStatus.OK.value())
                .message("리뷰 삭제 성공!")
                .data(null)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @PostMapping("/{reviewId}/reaction")
    public ResponseEntity<ResponseMessage<ReactionResponse>> createReaction(
            @PathVariable Long reviewId,
            @RequestBody ReactionRequest reactionRequest,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        log.error("asdadsad");
        ReactionResponse reactionResponse = reviewService.createReaction(reviewId, reactionRequest.getReactionType(), userDetails.getUser());

        String message = reactionResponse.getReactionStatus() ? "리액션 등록 성공!" : "리액션 취소 성공!";

        ResponseMessage<ReactionResponse> responseMessage = ResponseMessage.<ReactionResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(message)
                .data(reactionResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }


}
