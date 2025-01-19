package com.issuemoa.batch.infrastructure.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static LocalDateTime getStartOfMinusDays(int days) {
        return LocalDate.now().minusDays(days).atStartOfDay();
    }

    public static LocalDateTime getEndOfYesterday() {
        return LocalDate.now().minusDays(1).atTime(LocalTime.MAX);
    }

    /**
     * 현재로부터 한 달 전 금요일 날짜를 yyyyMMdd 형식으로 반환합니다.
     * @return 한 달 전 금요일 날짜 (yyyyMMdd 형식의 문자열)
     */
    public static String getLastMonthFriday() {
        // 현재 날짜로부터 한 달 전 계산
        LocalDate oneMonthAgo = LocalDate.now().minusMonths(1);

        // 한 달 전 날짜 기준으로 가장 가까운 금요일 찾기
        LocalDate lastFriday = oneMonthAgo.with(java.time.temporal.TemporalAdjusters.previousOrSame(DayOfWeek.FRIDAY));

        // yyyyMMdd 형식으로 포맷팅
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return lastFriday.format(formatter);
    }
}
