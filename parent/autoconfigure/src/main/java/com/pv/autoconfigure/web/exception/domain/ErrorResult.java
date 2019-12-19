package com.pv.autoconfigure.web.exception.domain;

import com.pv.commons.domain.CodeAble;
import com.pv.commons.domain.CodeAndMessageAble;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import javax.annotation.Nullable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Accessors(fluent = true)
@Getter
@RequiredArgsConstructor(staticName = "of")
public class ErrorResult implements CodeAndMessageAble {
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
