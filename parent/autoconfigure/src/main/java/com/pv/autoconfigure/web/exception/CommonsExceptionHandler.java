package com.pv.autoconfigure.web.exception;

import com.pv.autoconfigure.web.exception.domain.ErrorResult;
import com.pv.autoconfigure.web.exception.util.ValidatorUtils;
import com.pv.commons.exception.TokenException;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Map;

@Order(Ordered.HIGHEST_PRECEDENCE + 1)
@RestControllerAdvice
@Log4j2
class CommonsExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    ErrorResult handle(ConstraintViolationException e) {
        log.error(e.getMessage(), e);

        return ErrorResult.of(this.getMessageFromMap(ValidatorUtils.fromConstraintViolations(e.getConstraintViolations())));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ErrorResult handle(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);

        return ErrorResult.of(this.getMessageFromMap(ValidatorUtils.fromMethodArgumentNotValidException(e)));
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({TokenException.class})
    ErrorResult handle(TokenException e) {
        log.error(e.getMessage(), e);

        return ErrorResult.of(e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    ErrorResult handle(Exception e) {
        log.error(e.getMessage(), e);

        return ErrorResult.of(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }

    @NotBlank
    private String getMessageFromMap(@NotEmpty Map<@NotBlank String, @NotBlank String> map) {
        return map.entrySet().stream().findFirst().map(entry -> entry.getKey() + " " + entry.getValue()).orElseThrow();
    }
}
