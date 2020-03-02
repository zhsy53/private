package com.zch.autoconfigure.exception.domain;

import com.zch.commons.domain.CodeAble;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class ErrorResult {
    @NotBlank
    private final String code;
    @Nullable
    private final String message;

    @NotNull
    public static ErrorResult fromException(@NotNull Exception e) {
        return of(CodeAble.extractFromException(e), e.getMessage());
    }

    @NotNull
    public static ErrorResult fromException(@NotNull Exception e, @NotNull String message) {
        return of(CodeAble.extractFromException(e), message);
    }
}
