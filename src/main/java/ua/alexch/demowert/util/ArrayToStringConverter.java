package ua.alexch.demowert.util;

import java.util.StringJoiner;

import com.fasterxml.jackson.databind.util.StdConverter;

import ua.alexch.demowert.WertOptions;

/**
 * Helper class for converting a {@code String} array into a {@code String}
 * delimited by specific symbol.
 */
public class ArrayToStringConverter extends StdConverter<String[], String> {

    @Override
    public String convert(String[] value) {
        if (value == null || value.length == 0) {
            return "";
        }

        if (value.length == 1) {
            return value[0] == null ? "" : value[0];
        }

        StringJoiner result = new StringJoiner(WertOptions.DELIMITER);
        for (String s : value) {
            if (s != null && !s.isEmpty()) {
                result.add(s);
            }
        }

        return result.toString();
    }
}
