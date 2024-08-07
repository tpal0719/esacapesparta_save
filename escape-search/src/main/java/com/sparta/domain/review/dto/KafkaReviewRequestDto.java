package com.sparta.domain.review.dto;

import com.sparta.global.util.KafkaDtoUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KafkaReviewRequestDto implements KafkaDtoUtil {
    private String requestId;
    private Long storeId;
    private Long themeId;
}
