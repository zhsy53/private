package com.zch.autoconfigure.converter;

import com.zch.commons.util.StringKit;
import com.zch.commons.util.TimeUtils;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;

final class LocalDateTimeConverter implements Converter<String, LocalDateTime> {
    @Override
    public LocalDateTime convert(String source) {
        return StringKit.trim(source).map(TimeUtils::parseLocalDateTimeFromString).orElse(null);
    }
}
