package com.pv.autoconfigure.web.exception;

import com.pv.autoconfigure.web.exception.domain.ErrorResult;
import com.pv.commons.domain.Language;
import com.pv.commons.exception.CustomException;
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
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
@RestControllerAdvice
@Log4j2
class CustomExceptionHandler {
    @ExceptionHandler(CustomException.class)
    ErrorResult handle(CustomException e, HttpServletRequest request) {
        String acceptLanguage = request.getHeader(ACCEPT_LANGUAGE);

        log.debug("{}:{}", ACCEPT_LANGUAGE, acceptLanguage);

        return ErrorResult.of(e.getMessage(), Language.from(acceptLanguage));
    }
}
