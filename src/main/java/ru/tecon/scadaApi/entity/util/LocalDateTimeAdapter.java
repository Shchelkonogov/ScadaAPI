package ru.tecon.scadaApi.entity.util;

import ru.tecon.scadaApi.Constants;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;

/**
 * Адаптер для разбора форматы даты из json/xml
 * @author Maksim Shchelkonogov
 */
public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {

    @Override
    public LocalDateTime unmarshal(String dateString) {
        return LocalDateTime.parse(dateString, Constants.DATE_TIME_FORMATTER);
    }

    @Override
    public String marshal(LocalDateTime localDateTime) {
        return Constants.DATE_TIME_FORMATTER.format(localDateTime);
    }
}
