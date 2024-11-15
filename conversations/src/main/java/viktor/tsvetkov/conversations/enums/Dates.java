package viktor.tsvetkov.conversations.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class Dates {

    @RequiredArgsConstructor
    @Getter
    public enum DaysOfWeek {

        MONDAY("понедельник", "пон", "monday", "mon"),
        TUESDAY("вторник", "вт", "tuesday", "tue"),
        WEDNESDAY("среда", "ср", "wednesday", "wed"),
        THURSDAY("четверг", "чт", "thursday", "thr"),
        FRIDAY("пятница", "пт", "friday", "frid"),
        SATURDAY("суббота", "сб", "saturday", "st"),
        SUNDAY("воскресенье", "вс", "sunday", "sn");

        private final String ruName;
        private final String shortRuName;
        private final String engName;
        private final String shortEngName;
    }

    @RequiredArgsConstructor
    @Getter
    public enum Day {

        TODAY("сегодня", "today"),
        YESTERDAY("вчера", "yesterday");

        private final String ruName;
        private final String engName;
    }

    @RequiredArgsConstructor
    @Getter
    public enum Months {

        JANUARY("январь", "янв", "января", "january", "jan"),
        FEBRUARY("февраль", "фев", "февраля", "february", "feb"),
        MARCH("март", "мар", "марта", "march", "mar"),
        APRIL("апрель", "апр", "апреля", "april", "apr"),
        MAY("май", "май", "мая", "may", "may"),
        JUNE("июнь", "июн", "июня", "june", "jun"),
        JULY("июль", "июл", "июля", "july", "jul"),
        AUGUST("август", "авг", "августа", "august", "aug"),
        SEPTEMBER("сентябрь", "сен", "сентября", "september", "sep"),
        OCTOBER("октябрь", "окт", "октября", "october", "oct"),
        NOVEMBER("ноябрь", "ноя", "ноября", "november", "nov"),
        DECEMBER("декабрь", "дек", "декабря", "december", "dec");

        private final String ruName;
        private final String shortRuName;
        private final String caseSpecificRuName;
        private final String engName;
        private final String shortEngName;
    }
}
