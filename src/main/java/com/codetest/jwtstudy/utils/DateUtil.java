package com.codetest.jwtstudy.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {

    public static LocalDate dateToLocalDate(Date date) {
        return LocalDate.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public static LocalDateTime dateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public static LocalDate stringToLocalDate(String date, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);

        return LocalDate.parse(date, formatter);
    }

    public static LocalDateTime stringToLocalDateTime(String date, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);

        return LocalDateTime.parse(date, formatter);
    }

}
