package ru.tecon.scadaApi.entity.util;

import ru.tecon.scadaApi.Constants;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ParamConverter;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 * Конвертер для JAX-RS {@link javax.ws.rs.QueryParam} тип {@link LocalDateTime}
 * @author Maksim Shchelkonogov
 */
public class LocalDateTimeConverter implements ParamConverter<LocalDateTime> {

    @Override
    public LocalDateTime fromString(String value) {
        try {
            return LocalDateTime.parse(value, Constants.DATE_TIME_FORMATTER);
        } catch (DateTimeParseException ex) {
            throw new WebApplicationException(ex, Response.Status.BAD_REQUEST);
        } catch (NullPointerException ex) {
            return null;
        }
    }

    @Override
    public String toString(LocalDateTime value) {
        return Constants.DATE_TIME_FORMATTER.format(value);
    }
}
