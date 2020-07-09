package ua.alexch.demowert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.databind.util.StdConverter;

public class DateToStringConverter extends StdConverter<LocalDate, String> {

    @Override
    public String convert(LocalDate value) {

        return value == null ? "" : value.format(DateTimeFormatter.ofPattern(WertOptions.DATE_FORMAT));
    }

}
