package com.pv.commons.exception;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class SerializationException extends RuntimeException {
    public SerializationException() {
        super();
    }

    public SerializationException(@NotBlank String message) {
        super(message);
    }

    public SerializationException(@NotBlank String message, @NotNull Throwable cause) {
        super(message, cause);
    }

    public SerializationException(@NotNull Throwable cause) {
        super(cause);
    }
}
