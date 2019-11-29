package com.pv.commons.exception;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ArgumentException extends RuntimeException {
    public ArgumentException() {
        super();
    }

    public ArgumentException(@NotBlank String message) {
        super(message);
    }

    public ArgumentException(@NotBlank String message, @NotNull Throwable cause) {
        super(message, cause);
    }

    public ArgumentException(@NotNull Throwable cause) {
        super(cause);
    }
}
