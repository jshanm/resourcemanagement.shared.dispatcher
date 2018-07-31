package com.ebsco.dispatcher.util;

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Builder for create Date
 *
 * @author gsilva-ebsco
 */
public class DateBuilder {

    private Instant instantBuilder;


    private DateBuilder() {
        this.instantBuilder = Instant.now();
    }

    private DateBuilder(Date date) {
        this.instantBuilder = date.toInstant();
    }

    /**
     * Set current date on builder
     *
     * @return DateBuilder
     */
    public static DateBuilder now() {
        return new DateBuilder();
    }

    public static DateBuilder fromDate(Date date) {
        return new DateBuilder(date);
    }

    public static DateBuilder fromMillis(long delta) {
        return new DateBuilder(Date.from(Instant.ofEpochMilli(delta)));
    }

    /**
     * Verify if date is before that now
     *
     * @return true if is before
     */
    public boolean isBeforeNow() {
        return this.instantBuilder.isBefore(Instant.now());
    }

    /**
     * Add time on date
     *
     * @param delta
     * @return DateBuilder
     */
    public DateBuilder addSeconds(long delta) {
        this.instantBuilder = this.instantBuilder.plusSeconds(delta);
        return this;
    }

    public DateBuilder addDays(long delta) {
        this.instantBuilder = this.instantBuilder.plusSeconds(TimeUnit.DAYS.toSeconds(delta));
        return this;
    }

    public long buildToMillis() {
        return this.instantBuilder.toEpochMilli();
    }

    /**
     * Get builder Date
     *
     * @return Date
     */
    public Date build() {
        return Date.from(instantBuilder);
    }
}
