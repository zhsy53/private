package com.zch.autoconfigure.redis;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.zch.autoconfigure.ZchProperties;
import com.zch.autoconfigure.json.module.JavaTimeModule;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * https://github.com/FasterXML/jackson-docs/wiki/JacksonPolymorphicDeserialization
 * https://www.baeldung.com/jackson-inheritance
 */
@ConditionalOnClass({RedisConnectionFactory.class, ObjectMapper.class})
@EnableConfigurationProperties(ZchProperties.class)
@Configuration
public class RedisAutoConfiguration {
    @ConditionalOnMissingBean
    @Bean
    GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer() {
        var mapper = new ObjectMapper();

        var validator = BasicPolymorphicTypeValidator.builder().allowIfBaseType(Object.class).build();
        mapper.activateDefaultTyping(validator, DefaultTyping.EVERYTHING, As.PROPERTY);

        mapper.registerModule(new JavaTimeModule());

        return new GenericJackson2JsonRedisSerializer(mapper);
    }

    @ConditionalOnMissingBean
    @Bean
    RedisCacheConfiguration redisCacheConfiguration(ZchProperties properties, GenericJackson2JsonRedisSerializer serializer) {
        return RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues()
                .entryTtl(Duration.ofMinutes(properties.getCache().getExpirationMinutes()))
                .serializeKeysWith(SerializationPair.fromSerializer(RedisSerializer.string()))
                .serializeValuesWith(SerializationPair.fromSerializer(serializer));
    }

    @ConditionalOnMissingBean
    @Bean
    RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory factory, GenericJackson2JsonRedisSerializer serializer) {
        var template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        var keySerializer = new StringRedisSerializer();

        template.setKeySerializer(keySerializer);
        template.setHashKeySerializer(keySerializer);

        template.setValueSerializer(serializer);
        template.setHashValueSerializer(serializer);

        template.afterPropertiesSet();

        return template;
    }
}
