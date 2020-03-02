package com.zch.commons.util;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.*;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public interface TimeUtils {
    DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    ZoneId DEFAULT_ZONE = ZoneId.systemDefault();

    static String formatLocalDateTime(@NotNull LocalDateTime time) {
        return formatLocalDateTime(time, DATE_TIME_FORMATTER);
    }

    static String formatLocalDateTime(@NotNull LocalDateTime time, @NotNull DateTimeFormatter formatter) {
        return time.format(formatter);
    }

    static String formatLocalDate(@NotNull LocalDate time) {
        return formatLocalDate(time, DATE_FORMATTER);
    }

    static String formatLocalDate(@NotNull LocalDate time, @NotNull DateTimeFormatter formatter) {
        return time.format(formatter);
    }

    static LocalDateTime parseLocalDateTimeFromString(@NotBlank String time) {
        return parseLocalDateTimeFromString(time, DATE_TIME_FORMATTER);
    }

    static LocalDateTime parseLocalDateTimeFromString(@NotBlank String time, @NotNull DateTimeFormatter formatter) {
        return LocalDateTime.parse(time, formatter);
    }

    static LocalDateTime parseLocalDateTimeFromSecond(long seconds) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(seconds), DEFAULT_ZONE);
    }

    static LocalDateTime parseLocalDateTimeFromMillis(long millis) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), DEFAULT_ZONE);
    }

    static LocalDate parseLocalDateFromString(@NotBlank String time) {
        return parseLocalDateFromString(time, DATE_FORMATTER);
    }

    static LocalDate parseLocalDateFromString(@NotBlank String time, @NotNull DateTimeFormatter formatter) {
        return LocalDate.parse(time, formatter);
    }

    static long toMillis(@NotNull LocalDateTime time) {
        return time.atZone(DEFAULT_ZONE).toInstant().toEpochMilli();
    }

    static long toSeconds(@NotNull LocalDateTime time) {
        return time.atZone(DEFAULT_ZONE).toEpochSecond();
    }

    static Date toDate(@NotNull LocalDateTime dateTime) {
        return Date.from(dateTime.atZone(DEFAULT_ZONE).toInstant());
    }

    static LocalDateTime toDateTime(@NotNull Date date) {
        return date.toInstant().atZone(DEFAULT_ZONE).toLocalDateTime();
    }

    static boolean isBetween(@NotNull ChronoLocalDate current, @NotNull ChronoLocalDate begin, @NotNull ChronoLocalDate end) {
        return current.toEpochDay() >= begin.toEpochDay() && current.toEpochDay() <= end.toEpochDay();
    }

    static boolean isBetween(@NotNull LocalTime current, @NotNull LocalTime begin, @NotNull LocalTime end) {
        return (current.isAfter(begin) || current.equals(begin)) && (current.isBefore(end) || current.equals(end));
    }
}
