package ua.alexch.demowert.util;

import com.fasterxml.jackson.databind.util.StdConverter;

import ua.alexch.demowert.WertOptions;

/**
 * Helper class for converting a {@code String} object containing a specific
 * delimiter symbol into an array of {@code Strings}, computed by splitting
 * around the delimiter.
 */
public class StringToArrayConverter extends StdConverter<String, String[]> {

    @Override
    public String[] convert(String value) {
        if (value == null || value.isEmpty()) {
            return new String[0];
        }

        return value.split(WertOptions.DELIMITER);

//        return StringUtils.delimitedListToStringArray(value, WertOptions.DELIMITER);
    }
}
