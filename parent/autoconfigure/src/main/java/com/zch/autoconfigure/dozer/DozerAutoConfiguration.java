package com.zch.autoconfigure.dozer;

import io.craftsman.creator.CreatorFactory;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnClass({Mapper.class, CreatorFactory.class})
@Configuration
class DozerAutoConfiguration {
    @Bean
    DozerBeanMapper beanMapper() {
        return DozerFactory.MAPPER;
    }
}
