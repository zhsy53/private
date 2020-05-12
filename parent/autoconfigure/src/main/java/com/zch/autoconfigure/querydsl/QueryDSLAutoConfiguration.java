package com.zch.autoconfigure.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@ConditionalOnClass({JPAQueryFactory.class, EntityManager.class})
@Configuration
class QueryDSLAutoConfiguration {
    @Bean
    JPAQueryFactory jpaQueryFactory(EntityManager manager) {
        return new JPAQueryFactory(manager);
    }
}
