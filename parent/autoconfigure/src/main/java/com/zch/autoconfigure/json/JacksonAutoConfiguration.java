package com.zch.autoconfigure.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.zch.commons.domain.PageData;
import com.zch.commons.util.TimeUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Log4j2
@ConditionalOnProperty(prefix = "zch.enabled", name = "jackson", havingValue = "true", matchIfMissing = true)
@AutoConfigureBefore(org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration.class)
@ConditionalOnClass(ObjectMapper.class)
@Configuration
public class JacksonAutoConfiguration {
    @ConditionalOnMissingBean
    @ConditionalOnClass(Jdk8Module.class)
    @Bean
    Jdk8Module jdk8Module() {
        return new Jdk8Module();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Bean
    SimpleModule module(List<StdSerializer> serializers, List<StdDeserializer> deserializers) {
        log.info("Inject SimpleModule:");
        serializers.forEach(serializer -> log.info(serializer.getClass().getSimpleName()));
        deserializers.forEach(deserializer -> log.info(deserializer.getClass().getSimpleName()));

        SimpleModule module = new SimpleModule();

        module.addSerializer(LocalDate.class, new LocalDateSerializer(TimeUtils.DATE_FORMATTER));
        module.addDeserializer(LocalDate.class, new LocalDateDeserializer(TimeUtils.DATE_FORMATTER));

        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(TimeUtils.DATE_TIME_FORMATTER));
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(TimeUtils.DATE_TIME_FORMATTER));

        module.addSerializer(long.class, new LongJsonSerializer());
        module.addSerializer(Long.class, new LongJsonSerializer());

        serializers.forEach(serializer -> module.addSerializer(serializer.handledType(), serializer));

        deserializers.forEach(deserializer -> module.addDeserializer(deserializer.handledType(), deserializer));

        return module;
    }

    @ConditionalOnClass(Page.class)
    @Bean
    PageSerializer pageSerializer() {
        return new PageSerializer();
    }

    @ConditionalOnClass(PageData.class)
    @Bean
    PageDataSerializer pageDataSerializer() {
        return new PageDataSerializer();
    }

    @Bean
    TrimStringDeserializer trimStringDeserializer() {
        return new TrimStringDeserializer();
    }
}
