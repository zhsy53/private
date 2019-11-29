package com.pv.autoconfigure.web.converter;

import org.springframework.core.convert.converter.Converter;

final class TrimStringConverter implements Converter<String, String> {
    @Override
    public String convert(String source) {
        return source.trim();
    }
}
