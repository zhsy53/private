package com.pv.commons.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@FunctionalInterface
public interface Internationalizable {
    @NotBlank String display(@NotNull Language language);

    @NotBlank
    default String display(@NotBlank String language) {
        return this.display(Language.from(language));
    }
}
