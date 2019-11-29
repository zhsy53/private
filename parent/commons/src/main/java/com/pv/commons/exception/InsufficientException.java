package com.pv.commons.exception;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class InsufficientException extends RuntimeException {
    public InsufficientException() {
    }

    public InsufficientException(@NotBlank String message) {
        super(message);
    }

    public InsufficientException(@NotBlank String message, @NotNull Throwable cause) {
        super(message, cause);
    }

    public InsufficientException(@NotNull Throwable cause) {
        super(cause);
    }
}
