package com.utils.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

public class DateUtils {

    public DateUtils() {
    }

    private static final List<DateTimeFormatter> FORMATS = Arrays.asList(
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("dd-MM-yyyy"),
            DateTimeFormatter.ofPattern("MM-dd-yyyy")
    );

    public static String formatDateToMMYY(String date) {
        return formatDate(date, "MMyy");
    }

    public static String formatDateToYYMM(String date) {
        return formatDate(date, "yyMM");
    }

    public static String formatDateToDDMMYY(String date) {
        return formatDate(date, "ddMMyy");
    }

    private static String formatDate(String date, String pattern) {
        LocalDate localDate = parseDate(date);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return localDate.format(formatter);
    }

    private static LocalDate parseDate(String date) {
        for (DateTimeFormatter format : FORMATS) {
            System.err.println("For de formatos: "+format);
            try {
                return LocalDate.parse(date, format);
            } catch (DateTimeParseException e) {
                continue;
            }
        }
        throw new IllegalArgumentException("Formato de fecha no compatible");
    }
}

