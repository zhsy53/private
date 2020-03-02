package com.zch.commons.exception;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ExistsException extends RuntimeException {
    public ExistsException() {
    }

    public ExistsException(@NotBlank String message) {
        super(message);
    }

    public ExistsException(@NotBlank String message, @NotNull Throwable cause) {
        super(message, cause);
    }

    public ExistsException(@NotNull Throwable cause) {
        super(cause);
    }
}
