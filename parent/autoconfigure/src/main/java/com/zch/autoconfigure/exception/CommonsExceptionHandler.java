package com.zch.autoconfigure.exception;

import com.zch.autoconfigure.ZchProperties;
import com.zch.autoconfigure.exception.domain.ErrorResult;
import com.zch.autoconfigure.exception.util.ValidatorUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
@EnableConfigurationProperties(ZchProperties.class)
@Log4j2
@RequiredArgsConstructor
class CommonsExceptionHandler {
    private final ZchProperties zchProperties;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    ErrorResult handle(ConstraintViolationException e) {
        log.error(e.getMessage(), e);

        return ErrorResult.fromException(e, this.getMessageFromMap(ValidatorUtils.fromConstraintViolations(e.getConstraintViolations())));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ErrorResult handle(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);

        return ErrorResult.fromException(e, this.getMessageFromMap(ValidatorUtils.fromMethodArgumentNotValidException(e)));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    ErrorResult handle(Exception e) {
        log.error(e.getMessage(), e);

        boolean debug = zchProperties.getLog().isDebug();

        return ErrorResult.of(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), debug ? e.getMessage() : null);
    }

    @NotBlank
    private String getMessageFromMap(@NotEmpty Map<@NotBlank String, @NotBlank String> map) {
        return map.entrySet().stream().findFirst().map(entry -> entry.getKey() + " " + entry.getValue()).orElseThrow();
    }
}
