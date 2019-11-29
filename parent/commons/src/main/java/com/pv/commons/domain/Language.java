package com.pv.commons.domain;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

public enum Language {
    ZH_CN, ZH_TW, ZH_HK, EN_US;

    public static final Language DEFAULT = Language.ZH_CN;

    @NotNull
    public static Language from(@Nullable String s) {
        if (s == null || s.trim().isBlank()) {
            return DEFAULT;
        }

        for (Language language : values()) {
            if (language.toString().equalsIgnoreCase(s)) {
                return language;
            }
        }

        return DEFAULT;
    }
}
