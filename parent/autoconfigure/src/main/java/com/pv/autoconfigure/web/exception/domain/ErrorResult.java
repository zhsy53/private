package com.pv.autoconfigure.web.exception.domain;

import com.pv.commons.domain.Language;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public final class ErrorResult extends DefaultInternationalizedMessageHolder {
    @NotNull
    private final Language language;

    private ErrorResult(@NotBlank String message, @NotNull Language language) {
        super(message);
        this.language = language;
    }

    @NotNull
    public static ErrorResult of(@NotBlank String message, @NotNull Language language) {
        return new ErrorResult(message, language);
    }

    @NotNull
    public static ErrorResult of(@NotBlank String message) {
        return of(message, Language.DEFAULT);
    }

    public @NotBlank String display() {
        return this.display(language);
    }
}
