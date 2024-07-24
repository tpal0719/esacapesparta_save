package com.sparta.domain.escapeRoom.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.domain.escapeRoom.entity.Theme;
import com.sparta.domain.escapeRoom.entity.QEscapeRoom;
import com.sparta.domain.store.entity.Store;
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
public class EscapeRoomRepositoryImpl implements EscapeRoomRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Theme> findByStore(Store store, Pageable pageable) {
        QEscapeRoom escapeRoom = QEscapeRoom.escapeRoom;

        JPAQuery<Theme> query = jpaQueryFactory.selectFrom(escapeRoom)
                .where(escapeRoom.store.eq(store))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        for (Sort.Order order : pageable.getSort()) {
            PathBuilder<Theme> pathBuilder = new PathBuilder<>(escapeRoom.getType(), escapeRoom.getMetadata());
            query.orderBy(new OrderSpecifier<>(
                    order.isAscending() ? Order.ASC : Order.DESC,
                    pathBuilder.get(order.getProperty(), Comparable.class)
            ));
        }

        JPAQuery<Long> total = jpaQueryFactory.select(escapeRoom.count())
                .from(escapeRoom)
                .where(escapeRoom.store.eq(store));

        List<Theme> results = query.fetch();

        return PageableExecutionUtils.getPage(results, pageable, () -> Optional.ofNullable(total.fetchOne()).orElse(0L));


    }
}
