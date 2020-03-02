package com.zch.autoconfigure.converter;

import com.zch.commons.util.StringKit;
import com.zch.commons.util.TimeUtils;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;

final class LocalDateConverter implements Converter<String, LocalDate> {
    @Override
    public LocalDate convert(String source) {
        return StringKit.trim(source).map(TimeUtils::parseLocalDateFromString).orElse(null);
    }
}
