package com.pv.autoconfigure.web.exception.domain;

import com.pv.autoconfigure.web.exception.util.DisplayUtils;
import com.pv.commons.domain.Language;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Accessors(fluent = true)
@Getter
@RequiredArgsConstructor(staticName = "of")
public class InternationalizeErrorResult {
    @NotBlank
    private final String code;
    @NotBlank
    private final String message;
    @NotBlank
    private final String display;

    @NotBlank
    public static InternationalizeErrorResult from(@NotBlank String code, @NotBlank String message, @NotNull Language language) {
        return of(code, message, DisplayUtils.display(language, code));
    }
}
