package com.sparta.domain.review.repository;

import com.sparta.domain.review.entity.Review;
import com.sparta.global.exception.customException.ReviewException;
import com.sparta.global.exception.errorCode.ReviewErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review,Long>, ReviewRepositoryCustom{

    default Review findByIdOrElse(Long id){
        return findById(id).orElseThrow(
                () -> new ReviewException(ReviewErrorCode.REVIEW_NOT_FOUND));
    }
}
