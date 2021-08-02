package com.esmc.mcnp.domain.entity.converters;

import java.sql.Time;
import java.time.LocalTime;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocalTimeConverter implements AttributeConverter<LocalTime, Time> {

    @Override
    public Time convertToDatabaseColumn(LocalTime ltime) {
        return ltime == null ? null : Time.valueOf(ltime);
    }

    @Override
    public LocalTime convertToEntityAttribute(Time value) {
        return value == null ? null : value.toLocalTime();
    }

}
