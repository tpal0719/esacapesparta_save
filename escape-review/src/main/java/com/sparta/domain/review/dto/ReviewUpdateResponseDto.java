package com.sparta.domain.review.dto;

import com.sparta.domain.review.entity.Review;
import lombok.Getter;

@Getter
public class ReviewUpdateResponseDto {
    private Long reviewId;
//    private String storeName;
//    private String themeName;
    private String title;
    private String contents;
    private Double rating;

    public ReviewUpdateResponseDto(Review review){
        this.reviewId = review.getId();
//        this.storeName = review.getTheme().getStore().getName();
//        this.themeName = review.getTheme().getTitle();
        this.title = review.getTitle();
        this.contents = review.getContents();
        this.rating = review.getRating();
    }
}
