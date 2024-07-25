package com.sparta.domain.review.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateReviewRequestDto {

//    @NotNull(message = "방탈출 카페 id가 없습니다.")
//    private Long StoreId;
//
//    @NotNull(message = "방탈출 카페  테마 id가 없습니다.")
//    private Long themeId;

    @NotNull(message = "예약 id가 없습니다.")
    private Long reservationId;

    @NotBlank(message = "리뷰 제목을 입력해주세요.")
    private String title;

    @NotBlank(message = "리뷰 내용을 입력해주세요.")
    private String contents;

    @NotNull(message = "별점을 입력해주세요.")
    private Double rating;
}
