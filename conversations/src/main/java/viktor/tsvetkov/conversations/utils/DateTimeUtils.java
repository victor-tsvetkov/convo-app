package viktor.tsvetkov.conversations.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static viktor.tsvetkov.conversations.enums.Dates.Months.JANUARY;
import static viktor.tsvetkov.conversations.enums.Dates.Months.FEBRUARY;
import static viktor.tsvetkov.conversations.enums.Dates.Months.MARCH;
import static viktor.tsvetkov.conversations.enums.Dates.Months.APRIL;
import static viktor.tsvetkov.conversations.enums.Dates.Months.MAY;
import static viktor.tsvetkov.conversations.enums.Dates.Months.JUNE;
import static viktor.tsvetkov.conversations.enums.Dates.Months.JULY;
import static viktor.tsvetkov.conversations.enums.Dates.Months.AUGUST;
import static viktor.tsvetkov.conversations.enums.Dates.Months.SEPTEMBER;
import static viktor.tsvetkov.conversations.enums.Dates.Months.OCTOBER;
import static viktor.tsvetkov.conversations.enums.Dates.Months.NOVEMBER;
import static viktor.tsvetkov.conversations.enums.Dates.Months.DECEMBER;
import static viktor.tsvetkov.conversations.enums.Dates.Day.TODAY;
import static viktor.tsvetkov.conversations.enums.Dates.Day.YESTERDAY;

public class DateTimeUtils {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");

    public static LocalDateTime parseStringToLocalDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime, dateTimeFormatter);
    }

    public static LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public static String transformDate(String date) {
        if (!date.equals(TODAY.getRuName()) && !date.equals(YESTERDAY.getRuName())) {
            String numbersOfDate = transformAndExtractNumbersFromDate(date);
            String month = transformMonth(extractMonthFromDate(date));
            return numbersOfDate + " " + month;
        }
        return date;
    }

    private static String transformAndExtractNumbersFromDate(String date) {
        if (date.charAt(0) == '0') {
            return date.substring(1, 3);
        }
        return date.substring(0, 2);
    }

    private static String extractMonthFromDate(String date) {
        return date.substring(date.indexOf(" ") + 1);
    }

    private static String transformMonth(String month) {
        if (month.equals(JANUARY.getRuName())) {
            month = JANUARY.getCaseSpecificRuName();
        } else if (month.equals(FEBRUARY.getRuName())) {
            month = FEBRUARY.getCaseSpecificRuName();
        } else if (month.equals(MARCH.getRuName())) {
            month = MARCH.getCaseSpecificRuName();
        } else if (month.equals(APRIL.getRuName())) {
            month = APRIL.getCaseSpecificRuName();
        } else if (month.equals(MAY.getRuName())) {
            month = MAY.getCaseSpecificRuName();
        } else if (month.equals(JUNE.getRuName())) {
            month = JUNE.getCaseSpecificRuName();
        } else if (month.equals(JULY.getRuName())) {
            month = JULY.getCaseSpecificRuName();
        } else if (month.equals(AUGUST.getRuName())) {
            month = AUGUST.getCaseSpecificRuName();
        } else if (month.equals(SEPTEMBER.getRuName())) {
            month = SEPTEMBER.getCaseSpecificRuName();
        } else if (month.equals(OCTOBER.getRuName())) {
            month = OCTOBER.getCaseSpecificRuName();
        } else if (month.equals(NOVEMBER.getRuName())) {
            month = NOVEMBER.getCaseSpecificRuName();
        } else if (month.equals(DECEMBER.getRuName())) {
            month = DECEMBER.getCaseSpecificRuName();
        }
        return month;
    }
}
