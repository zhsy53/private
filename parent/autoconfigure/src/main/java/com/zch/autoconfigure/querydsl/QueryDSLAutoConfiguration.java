package com.zch.autoconfigure.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@ConditionalOnClass(JPAQueryFactory.class)
@Configuration
class QueryDSLAutoConfiguration {
    @ConditionalOnBean(EntityManager.class)
    @Bean
    JPAQueryFactory jpaQueryFactory(EntityManager manager) {
        return new JPAQueryFactory(manager);
    }
}
