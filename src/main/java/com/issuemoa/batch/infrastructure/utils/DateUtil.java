package com.issuemoa.batch.infrastructure.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateUtil {
    public static LocalDateTime getStartOfYesterday() {
        return LocalDate.now().minusDays(1).atStartOfDay();
    }

    public static LocalDateTime getEndOfYesterday() {
        return LocalDate.now().minusDays(1).atTime(LocalTime.MAX);
    }
}
