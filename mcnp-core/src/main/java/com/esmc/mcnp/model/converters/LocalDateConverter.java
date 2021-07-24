package com.esmc.mcnp.model.converters;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Convert a {@link LocalDate} into database column representation as {@link Date} and back again.
 * 
 * @author Mawuli
 */
@Converter(autoApply = true)
public class LocalDateConverter implements AttributeConverter<LocalDate, Date> {

	@Override
	public Date convertToDatabaseColumn(LocalDate value) {
		return value == null ? null : Date.valueOf(value);
	}

	@Override
	public LocalDate convertToEntityAttribute(Date value) {
		return value == null ? null : value.toLocalDate();
	}
}
