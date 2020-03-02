package com.zch.autoconfigure.exception.util;

import com.zch.commons.domain.Language;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

import static java.lang.String.format;
import static java.nio.charset.StandardCharsets.UTF_8;

@Log4j2
public abstract class DisplayUtils {
    private static final String FILE_PATH_TEMPLATE = "locale/%s.properties";

    @NotBlank
    public static String display(@NotNull Language language, @NotBlank String key) {
        Properties properties = new Properties();

        String file = format(FILE_PATH_TEMPLATE, language.toString().toLowerCase());

        try {
            PropertiesLoaderUtils.fillProperties(properties, new EncodedResource(new ClassPathResource(file), UTF_8));
        } catch (IOException e) {
            log.error("load i18n resource {} error.", file);
            return key;
        }

        var value = properties.get(key);

        log.debug("display message => {}", value);

        return Optional.ofNullable(value).map(Object::toString).orElse(key);
    }
}
