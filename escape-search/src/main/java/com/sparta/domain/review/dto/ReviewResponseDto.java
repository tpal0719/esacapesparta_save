package com.sparta.domain.review.dto;

import com.sparta.domain.review.entity.Review;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewResponseDto {
    private Long reviewId;
    private Double rating;
    private String contents;
    private String email;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    public ReviewResponseDto(Review review){
        this.reviewId = review.getId();
        this.rating = review.getRating();
        this.contents = review.getContents();
        this.email = review.getUser().getEmail();
        this.createAt = review.getCreatedAt();
        this.updateAt = review.getUpdatedAt();
    }
}
