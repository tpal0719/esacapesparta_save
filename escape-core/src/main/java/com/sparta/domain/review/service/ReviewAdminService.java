package com.sparta.domain.review.service;

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
     * @param user
     * @author SEMI
     */
    @Transactional(readOnly = true)
    public void deleteReview(Long reviewId, User user) {
        validateAuthority(user);
        reviewRepository.deleteById(reviewId);
    }



    /* Utils */


    /**
     * TODO : 권한 확인
     *
     * @param user
     * @author SEMI
     */
    public void validateAuthority(User user) {
        // admin 이용자
        if (user.getUserType() == UserType.ADMIN) {
            return;
        } else {
            throw new UserException(UserErrorCode.USER_NOT_AUTHORITY);
        }
    }
}
