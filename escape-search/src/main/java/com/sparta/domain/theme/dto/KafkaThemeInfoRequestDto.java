package com.sparta.domain.theme.dto;

import com.sparta.global.util.KafkaDtoUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KafkaThemeInfoRequestDto implements KafkaDtoUtil {
    private String requestId;
    private Long storeId;
    private Long themeId;
}
