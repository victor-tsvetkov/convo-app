package viktor.tsvetkov.conversations.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");

    public static LocalDateTime parseStringToLocalDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime, dateTimeFormatter);
    }
}
