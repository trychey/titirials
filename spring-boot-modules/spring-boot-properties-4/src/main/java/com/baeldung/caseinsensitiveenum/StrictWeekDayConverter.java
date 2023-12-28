package com.baeldung.caseinsensitiveenum;

import org.springframework.core.convert.converter.Converter;

public class StrictWeekDayConverter implements Converter<String, WeekDays> {
    @Override
    public WeekDays convert(final String source) {
        try {
            return WeekDays.valueOf(source.trim());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
