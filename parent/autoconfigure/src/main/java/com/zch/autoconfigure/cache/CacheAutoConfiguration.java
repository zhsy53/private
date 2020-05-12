package com.zch.autoconfigure.cache;

import com.zch.autoconfigure.ZchProperties;
import com.zch.autoconfigure.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.lang.reflect.Method;

import static org.springframework.util.StringUtils.arrayToDelimitedString;

@AutoConfigureAfter(RedisAutoConfiguration.class)
@EnableConfigurationProperties(ZchProperties.class)
@ConditionalOnBean({RedisConnectionFactory.class, RedisCacheConfiguration.class})
@Configuration
class CacheAutoConfiguration {
    @Primary
    @Bean
    KeyGenerator customKeyGenerator() {
        return new KeyGenerator() {
            private static final String DELIMITER = "_";

            @Override
            public Object generate(Object target, Method method, Object... params) {
                return target.getClass().getSimpleName() + DELIMITER + method.getName() + DELIMITER + arrayToDelimitedString(params, DELIMITER);
            }
        };
    }

    @Bean
    CachingConfigurer cachingConfigurerSupport(RedisConnectionFactory factory, RedisCacheConfiguration configuration) {
        return new CachingConfigurerSupport() {
            @Override
            public CacheManager cacheManager() {
                return RedisCacheManager.builder(RedisCacheWriter.nonLockingRedisCacheWriter(factory))
                        .cacheDefaults(configuration)
                        .build();
            }

            @Override
            public KeyGenerator keyGenerator() {
                return customKeyGenerator();
            }
        };
    }
}
