package com.zch.commons.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@FunctionalInterface
public interface CodeAble<T> {
    @NotBlank
    static String extractFromException(@NotNull Exception e) {
        var className = e.getClass().getSimpleName();
        return className.substring(0, className.length() - "Exception".length());
    }

    @NotNull T code();
}
