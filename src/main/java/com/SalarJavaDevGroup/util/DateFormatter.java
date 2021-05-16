package com.SalarJavaDevGroup.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract  class DateFormatter {
    public static String getDateBasic(LocalDateTime tmp) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return tmp.format(formatter);
    }
}
