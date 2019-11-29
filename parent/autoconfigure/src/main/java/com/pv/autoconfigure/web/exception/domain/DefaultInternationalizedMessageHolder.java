package com.pv.autoconfigure.web.exception.domain;

import com.pv.autoconfigure.web.exception.util.DisplayUtils;
import com.pv.commons.domain.InternationalizedMessageHolder;
import com.pv.commons.domain.Language;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
class DefaultInternationalizedMessageHolder implements InternationalizedMessageHolder {
    @NotBlank
    private final String message;

    @Override
    public @NotBlank String display(@NotNull Language language) {
        return DisplayUtils.display(language, this.message);
    }

    @Override
    public @NotBlank String message() {
        return this.message;
    }
}
