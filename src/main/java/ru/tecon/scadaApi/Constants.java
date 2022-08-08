package ru.tecon.scadaApi;

import java.time.format.DateTimeFormatter;

/**
 * Программные константы
 * @author Maksim Shchelkonogov
 */
public class Constants {

    public static final String DATE_TIME_FORMAT = "dd.MM.yyyy HH:mm:ss";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
}
