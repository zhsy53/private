package com.pv.autoconfigure.web.exception;

import com.pv.autoconfigure.web.exception.domain.ErrorResult;
import com.pv.autoconfigure.web.exception.domain.InternationalizeErrorResult;
import com.pv.commons.domain.Language;
import com.pv.commons.exception.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpHeaders.ACCEPT_LANGUAGE;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@Log4j2
class CustomExceptionHandler {
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(CustomException.class)
    InternationalizeErrorResult handle(CustomException e, HttpServletRequest request) {
        String acceptLanguage = request.getHeader(ACCEPT_LANGUAGE);

        log.debug("{}:{}", ACCEPT_LANGUAGE, acceptLanguage);

        return InternationalizeErrorResult.from(e.code(), e.message(), Language.from(acceptLanguage));
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({TokenException.class})
    ErrorResult handle(TokenException e) {
        log.error(e.getMessage(), e);

        return ErrorResult.fromException(e);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({
            ArgumentException.class,
            DeserializationException.class,
            ExistsException.class,
            ExpiredException.class,
            InsufficientException.class,
            NotFoundException.class,
            SerializationException.class
    })
    ErrorResult handle(Exception e) {
        log.error(e.getMessage(), e);

        return ErrorResult.fromException(e);
    }
}
