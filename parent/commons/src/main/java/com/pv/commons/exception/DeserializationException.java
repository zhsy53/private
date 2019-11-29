package com.pv.commons.exception;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class DeserializationException extends RuntimeException {
    public DeserializationException() {
        super();
    }

    public DeserializationException(@NotBlank String message) {
        super(message);
    }

    public DeserializationException(@NotBlank String message, @NotNull Throwable cause) {
        super(message, cause);
    }

    public DeserializationException(@NotNull Throwable cause) {
        super(cause);
    }
}
