package com.pv.commons.exception;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class NotFoundException extends RuntimeException {
    public NotFoundException() {
    }

    public NotFoundException(@NotBlank String message) {
        super(message);
    }

    public NotFoundException(@NotBlank String message, @NotNull Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(@NotNull Throwable cause) {
        super(cause);
    }
}
