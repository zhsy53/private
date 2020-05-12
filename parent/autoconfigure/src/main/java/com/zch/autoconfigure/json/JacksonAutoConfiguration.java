package com.zch.autoconfigure.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.zch.autoconfigure.json.module.FixLongModule;
import com.zch.autoconfigure.json.module.JavaTimeModule;
import com.zch.commons.domain.PageData;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;

import java.util.List;

@Log4j2
@AutoConfigureBefore(org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration.class)
@ConditionalOnProperty(prefix = "zch.enabled", name = "jackson", havingValue = "true", matchIfMissing = true)
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
    SimpleModule autoModule(List<StdSerializer> serializers, List<StdDeserializer> deserializers) {
        var module = new SimpleModule();

        serializers.forEach(serializer -> module.addSerializer(serializer.handledType(), serializer));
        deserializers.forEach(deserializer -> module.addDeserializer(deserializer.handledType(), deserializer));

        return module;
    }

    @Bean
    SimpleModule javaTimeModule() {
        return new JavaTimeModule();
    }

    @Bean
    SimpleModule fixLongModule() {
        return new FixLongModule();
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
