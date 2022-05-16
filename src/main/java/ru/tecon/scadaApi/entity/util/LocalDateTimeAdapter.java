package ru.tecon.scadaApi.entity.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Адаптер для разбора форматы даты из json/xml
 * @author Maksim Shchelkonogov
 */
public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    @Override
    public LocalDateTime unmarshal(String dateString) {
        return LocalDateTime.parse(dateString, FORMATTER);
    }

    @Override
    public String marshal(LocalDateTime localDateTime) {
        return FORMATTER.format(localDateTime);
    }
}
