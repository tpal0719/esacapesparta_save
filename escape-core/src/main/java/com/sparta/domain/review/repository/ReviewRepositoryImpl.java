package com.sparta.domain.review.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.domain.reservation.entity.QReservation;
import com.sparta.domain.review.entity.QReview;
import com.sparta.domain.review.entity.Review;
import com.sparta.domain.theme.entity.QTheme;
import com.sparta.domain.theme.entity.Theme;
import com.sparta.domain.user.entity.QUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ReviewRepositoryImpl implements ReviewRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Review> findByThemeReview(Theme theme) {

        QReview review = QReview.review;
        QUser user = QUser.user;
        QReservation reservation = QReservation.reservation;
        QTheme qTheme = QTheme.theme;

        JPAQuery<Review> query = jpaQueryFactory.selectFrom(review)
                .leftJoin(review.user, user).fetchJoin()
                .leftJoin(review.reservation, reservation).fetchJoin()
                .leftJoin(reservation.theme, qTheme)
                .where(qTheme.eq(theme));

        return query.fetch();
    }
}
