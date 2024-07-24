package com.sparta.domain.theme.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.domain.store.entity.Store;
import com.sparta.domain.theme.entity.QTheme;
import com.sparta.domain.theme.entity.Theme;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ThemeRepositoryImpl implements ThemeRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Theme> findByStore(Store store, Pageable pageable) {
        QTheme theme = QTheme.theme;

        JPAQuery<Theme> query = jpaQueryFactory.selectFrom(theme)
                .where(theme.store.eq(store))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        for (Sort.Order order : pageable.getSort()) {
            PathBuilder<Theme> pathBuilder = new PathBuilder<>(theme.getType(), theme.getMetadata());
            query.orderBy(new OrderSpecifier<>(
                    order.isAscending() ? Order.ASC : Order.DESC,
                    pathBuilder.get(order.getProperty(), Comparable.class)
            ));
        }

        JPAQuery<Long> total = jpaQueryFactory.select(theme.count())
                .from(theme)
                .where(theme.store.eq(store));

        List<Theme> results = query.fetch();

        return PageableExecutionUtils.getPage(results, pageable, () -> Optional.ofNullable(total.fetchOne()).orElse(0L));


    }
}
