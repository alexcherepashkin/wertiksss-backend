package ua.alexch.wertiksss.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.databind.util.StdConverter;

import ua.alexch.wertiksss.WertOptions;

/**
 * Helper class for converting {@link LocalDate} to a {@code String}.
 */
public class DateToStringConverter extends StdConverter<LocalDate, String> {

    @Override
    public String convert(LocalDate value) {

        return value == null ? "" : value.format(DateTimeFormatter.ofPattern(WertOptions.DATE_FORMAT));
    }

}
