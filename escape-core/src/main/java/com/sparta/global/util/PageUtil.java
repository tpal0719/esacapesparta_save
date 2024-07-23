package com.sparta.global.util;

import com.sparta.global.exception.customException.PageException;
import com.sparta.global.exception.errorCode.PageErrorCode;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageUtil {

    // 유틸리티 클래스의 인스턴스화 방지
    private PageUtil() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static Pageable createPageable(Integer pageNum, Integer pageSize, Boolean isDesc, String sortBy) {
        if (pageNum < 1) {
            throw new PageException(PageErrorCode.PAGE_NOT_FOUND);
        }

        Sort.Direction direction = isDesc ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortBy);

        return PageRequest.of(pageNum - 1, pageSize, sort);
    }
}