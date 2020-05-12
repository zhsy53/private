package com.zch.commons.dom.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Arrays;

public enum Type {
    VIDEO, IMG, TEXT;

    @NotNull
    public static Type from(@NotBlank String s) {
        return Arrays.stream(values()).filter(type -> type.name().equalsIgnoreCase(s)).findFirst().orElse(null);
    }
}