package com.zch.commons.exception;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ExpiredException extends RuntimeException {
    public ExpiredException() {
        super();
    }

    public ExpiredException(@NotBlank String message) {
        super(message);
    }

    public ExpiredException(@NotBlank String message, @NotNull Throwable cause) {
        super(message, cause);
    }

    public ExpiredException(@NotNull Throwable cause) {
        super(cause);
    }
}
