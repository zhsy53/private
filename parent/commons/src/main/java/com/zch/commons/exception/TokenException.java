package com.zch.commons.exception;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class TokenException extends ArgumentException {
    public TokenException() {
    }

    public TokenException(@NotBlank String message) {
        super(message);
    }

    public TokenException(@NotBlank String message, @NotNull Throwable cause) {
        super(message, cause);
    }

    public TokenException(@NotNull Throwable cause) {
        super(cause);
    }
}
