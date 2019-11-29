package com.pv.commons.exception;

import com.pv.commons.domain.MessageHolder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CustomException extends RuntimeException {
    private CustomException(@NotBlank String message) {
        super(message);
    }

    private CustomException(@NotBlank String message, @NotNull Throwable cause) {
        super(message, cause);
    }

    @NotNull
    public static CustomException from(@NotNull MessageHolder holder) {
        return new CustomException(holder.message());
    }

    @NotNull
    public static CustomException from(@NotNull MessageHolder holder, @NotNull Throwable cause) {
        return new CustomException(holder.message(), cause);
    }
}
