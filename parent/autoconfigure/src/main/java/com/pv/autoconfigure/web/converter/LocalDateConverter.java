package com.pv.autoconfigure.web.converter;

import com.pv.commons.util.StringKit;
import com.pv.commons.util.TimeUtils;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;

final class LocalDateConverter implements Converter<String, LocalDate> {
    @Override
    public LocalDate convert(String source) {
        return StringKit.trim(source).map(TimeUtils::parseLocalDateFromString).orElse(null);
    }
}
