package com.esmc.mcnp.core.utils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;

public class NextDayWeekendAdjuster  implements TemporalAdjuster {

    protected int amountToAdd = 30;

    public NextDayWeekendAdjuster() {
    }

    public NextDayWeekendAdjuster(int amountToAdd) {
        this.amountToAdd = amountToAdd;
    }

    @Override
    public Temporal adjustInto(Temporal temporal) {
        LocalDate currentDate = LocalDate.from(temporal);

        LocalDate futureDate = LocalDate.from(temporal).plus(amountToAdd,
                ChronoUnit.DAYS);

        // loop through date range checking if the date
        // is Saturday or Sunday by using the WeekendQuery
        // if it is, add one day to account for the business day
        for (LocalDate startDate = currentDate; startDate
                .isBefore(futureDate); startDate = startDate.plusDays(1)) {

            if (startDate.query(new IsWeekendOrHolidaysQuery())) {
                futureDate = futureDate.plusDays(1);
            }
        }
        return futureDate;
    }

    /*public static void main(String args[]) {

        LocalDate currentDateTime = LocalDate.of(2017, Month.DECEMBER, 1);

        LocalDate adjustedDate = currentDateTime
                .with(new NextDayWeekendAdjuster(90));

        System.out.println(adjustedDate);

    }*/
}
