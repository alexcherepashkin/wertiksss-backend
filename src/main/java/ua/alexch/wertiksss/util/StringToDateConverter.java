package ua.alexch.wertiksss.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.databind.util.StdConverter;

import ua.alexch.wertiksss.WertOptions;

/**
 * Helper class for converting {@code String} to {@link LocalDate}.
 */
public class StringToDateConverter extends StdConverter<String, LocalDate> {

    @Override
    public LocalDate convert(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        return LocalDate.parse(value, DateTimeFormatter.ofPattern(WertOptions.DATE_FORMAT));
    }

}
