package ru.tecon.scadaApi.entity.util;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.LocalDateTime;

/**
 * Провайдер для JAX-RS {@link javax.ws.rs.QueryParam} добавил тип {@link LocalDateTime}
 * @author Maksim Shchelkonogov
 */
@Provider
public class LocalDateTimeConverterProvider implements ParamConverterProvider {

    @Override
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {

        if (LocalDateTime.class.equals(rawType)) {
            @SuppressWarnings("unchecked")
            ParamConverter<T> converter = (ParamConverter<T>) new LocalDateTimeConverter();
            return converter;
        }

        return null;
    }
}
