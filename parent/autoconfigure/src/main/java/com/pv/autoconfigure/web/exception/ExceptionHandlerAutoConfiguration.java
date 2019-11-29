package com.pv.autoconfigure.web.exception;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type.SERVLET;

@ConditionalOnWebApplication(type = SERVLET)
@ConditionalOnProperty(prefix = "pv.enabled", name = "exception", havingValue = "true", matchIfMissing = true)
@Configuration
@Import(CustomExceptionHandler.class)
class ExceptionHandlerAutoConfiguration {
    @AutoConfigureAfter(ExceptionHandlerAutoConfiguration.class)
    @Configuration
    @Import(CommonsExceptionHandler.class)
    static class CommonsExceptionHandlerConfiguration {
    }
}
