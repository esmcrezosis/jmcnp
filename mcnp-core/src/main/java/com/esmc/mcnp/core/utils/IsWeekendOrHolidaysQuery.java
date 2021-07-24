package com.esmc.mcnp.core.utils;

import com.google.common.collect.Lists;

import java.time.Month;
import java.time.MonthDay;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQuery;
import java.util.List;

class IsWeekendOrHolidaysQuery implements TemporalQuery<Boolean> {

    static List<MonthDay> HOLIDAYS = Lists.newArrayList(
            MonthDay.of(Month.JANUARY, 1), // New Years Day	January 1
            MonthDay.of(Month.APRIL, 27), // Good Friday	April 18
            MonthDay.of(Month.MAY, 1), // Memorial Day	May 26
            MonthDay.of(Month.JUNE, 21), // Martyrs Day
            MonthDay.of(Month.AUGUST, 15), // Martyrs Day
            MonthDay.of(Month.NOVEMBER, 1), // Thanksgiving Day	November 27*
            MonthDay.of(Month.DECEMBER, 25) // Christmas	December 25***
    );
    
    @Override
    public Boolean queryFrom(TemporalAccessor temporal) {
        MonthDay currentMonthDay = MonthDay.from(temporal);
        return temporal.get(ChronoField.DAY_OF_WEEK) > 5 || HOLIDAYS.contains(currentMonthDay);
    }
}
