package com.zch.autoconfigure.cache;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.zch.autoconfigure.ZchProperties;
import com.zch.autoconfigure.json.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager.RedisCacheManagerBuilder;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;
import java.time.Duration;

import static org.springframework.util.StringUtils.arrayToDelimitedString;

@EnableConfigurationProperties(ZchProperties.class)
@AutoConfigureAfter(JacksonAutoConfiguration.class)
@ConditionalOnClass({Cache.class, RedisConnectionFactory.class})
@Configuration
class RedisAutoConfiguration {
    @Bean
    KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            private static final String DELIMITER = "_";

            @Override
            public Object generate(Object target, Method method, Object... params) {
                return target.getClass().getSimpleName() + DELIMITER + method.getName() + DELIMITER + arrayToDelimitedString(params, DELIMITER);
            }
        };
    }

    /**
     * https://github.com/FasterXML/jackson-docs/wiki/JacksonPolymorphicDeserialization
     *
     * @see GenericJackson2JsonRedisSerializer
     */
    @ConditionalOnClass(ObjectMapper.class)
    @Bean
    RedisSerializer<Object> jsonSerializer(ObjectMapper mapper) {
        var serializer = new Jackson2JsonRedisSerializer(Object.class);

//        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);

        BasicPolymorphicTypeValidator validator = BasicPolymorphicTypeValidator.builder().allowIfBaseType(Object.class).build();
        mapper.activateDefaultTyping(validator, DefaultTyping.NON_FINAL, As.PROPERTY);
        mapper.configure(MapperFeature.USE_ANNOTATIONS, false);

//        var filter = SimpleBeanPropertyFilter.serializeAllExcept("@class");
//        var filterProvider = new SimpleFilterProvider().addFilter("-@class", filter);
//        mapper.setFilterProvider(filterProvider);

//        mapper.setPolymorphicTypeValidator(Validity.DENIED);
        serializer.setObjectMapper(mapper);

        return serializer;
//
////        mapper.activateDefaultTyping();

//        return new GenericJackson2JsonRedisSerializer(mapper);
    }

    @Bean
    RedisCacheConfiguration redisCacheConfiguration(RedisSerializer<Object> serializer, ZchProperties properties) {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(properties.getCache().getExpirationMinutes()))
                .serializeKeysWith(SerializationPair.fromSerializer(RedisSerializer.string()))
                .serializeValuesWith(SerializationPair.fromSerializer(serializer));
    }

    @Bean
    CacheManager cacheManager(RedisConnectionFactory factory, RedisCacheConfiguration configuration) {
        return RedisCacheManagerBuilder.fromConnectionFactory(factory).cacheDefaults(configuration).build();
    }

    @Primary
    @Bean
    RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory factory, RedisSerializer<Object> serializer) {
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

    @Bean
    CachingConfigurer cachingConfigurerSupport(CacheManager cacheManager, KeyGenerator keyGenerator) {
        return new CachingConfigurerSupport() {
            @Override
            public CacheManager cacheManager() {
                return cacheManager;
            }

            @Override
            public KeyGenerator keyGenerator() {
                return keyGenerator;
            }
        };
    }
}
