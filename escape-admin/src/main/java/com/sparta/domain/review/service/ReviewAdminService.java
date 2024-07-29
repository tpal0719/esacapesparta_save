package com.sparta.domain.review.service;

import com.sparta.domain.review.entity.Review;
import com.sparta.domain.review.repository.ReviewRepository;
import com.sparta.domain.user.entity.User;
import com.sparta.domain.user.entity.UserType;
import com.sparta.global.exception.customException.UserException;
import com.sparta.global.exception.errorCode.UserErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewAdminService {

    public final ReviewRepository reviewRepository;

    /**
     * TODO : 리뷰 강제 삭제
     *
     * @param reviewId
     * @author SEMI
     */
    @Transactional
    public void deleteReview(Long reviewId) {

        Review review = reviewRepository.findByIdOrElse(reviewId);

        reviewRepository.delete(review);
    }


}
