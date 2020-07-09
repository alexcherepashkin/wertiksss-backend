package ua.alexch.demowert;

import com.fasterxml.jackson.databind.util.StdConverter;

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
