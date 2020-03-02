package com.zch.commons.exception;

import com.zch.commons.domain.CodeAndMessageAble;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CustomException extends RuntimeException implements CodeAndMessageAble {
    @NotBlank
    private final String code;

    private CustomException(@NotBlank String message, @NotBlank String code) {
        super(message);
        this.code = code;
    }

    private CustomException(@NotBlank String message, @NotBlank String code, @NotNull Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    @NotNull
    public static CustomException from(@NotNull CodeAndMessageAble codeAndMessageAble) {
        return new CustomException(codeAndMessageAble.message(), codeAndMessageAble.code());
    }

    @NotNull
    public static CustomException from(@NotNull CodeAndMessageAble codeAndMessageAble, @NotNull Throwable cause) {
        return new CustomException(codeAndMessageAble.message(), codeAndMessageAble.code(), cause);
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public @NotBlank String message() {
        return super.getMessage();
    }
}
