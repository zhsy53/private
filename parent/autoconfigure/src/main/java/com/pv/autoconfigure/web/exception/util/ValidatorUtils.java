package com.pv.autoconfigure.web.exception.util;

import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ConstraintViolation;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public interface ValidatorUtils {
    @NotNull
    static Map<@NotBlank String, @NotBlank String> fromConstraintViolations(@NotNull Set<ConstraintViolation<?>> constraintViolations) {
        var map = new HashMap<@NotBlank String, @NotBlank String>();

        if (!ObjectUtils.isEmpty(constraintViolations)) {
            for (ConstraintViolation<?> constraintViolation : constraintViolations) {
                PathImpl path = (PathImpl) constraintViolation.getPropertyPath();
                map.put(path.getLeafNode().asString(), constraintViolation.getMessage());
            }
        }

        return map;
    }

    @NotNull
    static Map<@NotBlank String, @NotBlank String> fromMethodArgumentNotValidException(@NotNull MethodArgumentNotValidException e) {
        var map = new HashMap<@NotBlank String, @NotBlank String>();

        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            map.put(error.getField(), error.getDefaultMessage());
        }

        return map;
    }
}
