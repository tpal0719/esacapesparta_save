package com.sparta.global.util;

import com.sparta.global.exception.customException.LocalDateTimeException;
import com.sparta.global.exception.errorCode.CommonErrorCode;
import com.sparta.global.exception.errorCode.LocalDateTimeErrorCode;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateTimeUtils {

    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static LocalDateTime parseStringToLocalDateTime(String dateTimeString) {
        try {
            LocalDateTime localDateTime = LocalDateTime.parse(dateTimeString, formatter);
            checkValidTime(localDateTime);
            return localDateTime;
        } catch (DateTimeParseException e) {
            throw new LocalDateTimeException(LocalDateTimeErrorCode.DATETIME_PARSE_ERROR);
        }
    }

    public static String parseLocalDateTimeToString(LocalDateTime localDateTime) {
        try {
            return localDateTime.format(formatter);
        } catch (DateTimeParseException e) {
            throw new LocalDateTimeException(LocalDateTimeErrorCode.DATETIME_PARSE_ERROR);
        }
    }

    public static void checkValidTime(LocalDateTime localDateTime) {
        if(localDateTime.isBefore(LocalDateTime.now())) {
            throw new LocalDateTimeException(LocalDateTimeErrorCode.INVALID_PAST_TIME);
        }
    }
}
