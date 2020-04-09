package com.zch.autoconfigure.mq;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@AutoConfigureAfter(JacksonAutoConfiguration.class)
@ConditionalOnClass(Message.class)
@Configuration
@Log4j2
class MqAutoConfiguration {
    //TODO
    @ConditionalOnClass(ObjectMapper.class)
    @Bean
    public MessageConverter messageConverter(ObjectMapper mapper) {
        log.info("mapper => {}", mapper);
        return new Jackson2JsonMessageConverter(mapper);
    }
}
