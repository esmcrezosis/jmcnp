package com.esmc.mcnp.domain.converters;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Convert a {@link LocalDateTime} into database column representation as {@link Date} and back again.
 * 
 * @author Mawuli
 */
@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

	@Override
	public java.sql.Timestamp convertToDatabaseColumn(LocalDateTime value) {
		return value == null ? null : Timestamp.valueOf(value);
	}

	@Override
	public LocalDateTime convertToEntityAttribute(Timestamp value) {
		return value == null ? null : value.toLocalDateTime();
	}
}
