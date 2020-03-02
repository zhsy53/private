package com.zch.commons.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@FunctionalInterface
public interface InternationalizeAble {
    @NotBlank String display(@NotNull Language language);

    @NotBlank
    default String display(@NotBlank String language) {
        return this.display(Language.from(language));
    }
}
