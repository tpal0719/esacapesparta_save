package com.sparta.domain.store.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.domain.store.entity.QStore;
import com.sparta.domain.store.entity.Store;
import com.sparta.global.util.PageUtil;
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
public class StoreRepositoryImpl implements StoreRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Store> findByName(String name, String area, Pageable pageable) {
        QStore store = QStore.store;

        JPAQuery<Store> query = jpaQueryFactory.selectFrom(store)
                .where(nameContains(name))
                .where(areaContains(area))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        for (Sort.Order order : pageable.getSort()) {
            PathBuilder<Store> pathBuilder = new PathBuilder<>(store.getType(), store.getMetadata());
            query.orderBy(new OrderSpecifier<>(
                    order.isAscending() ? Order.ASC : Order.DESC,
                    pathBuilder.get(order.getProperty(), Comparable.class)
            ));
        }

        JPAQuery<Long> total = jpaQueryFactory.select(store.count())
                .from(store)
                .where(nameContains(name));

        List<Store> results = query.fetch();

        //total 계산 쿼리를 지연로딩한다.
        return PageableExecutionUtils.getPage(results, pageable, () -> Optional.ofNullable(total.fetchOne()).orElse(0L));
//        return new PageImpl<>(results, pageable, Optional.ofNullable(total.fetchOne()).orElse(0L));
    }

    private BooleanExpression nameContains(String name) {
        QStore store = QStore.store;
        // name이 null이면 null을 반환하여 조건이 적용되지 않도록 함
        return name != null ? store.name.containsIgnoreCase(name) : null;
    }

    private BooleanExpression areaContains(String area) {
        QStore store = QStore.store;
        // name이 null이면 null을 반환하여 조건이 적용되지 않도록 함
        return area != null ? store.area.containsIgnoreCase(area) : null;
    }
}
