package com.pv.commons.util;

import javax.annotation.Nullable;
import java.util.Optional;

public interface StringKit {
    static Optional<String> trim(@Nullable String s) {
        return Optional.ofNullable(s).map(String::trim).filter(x -> !x.isBlank());
    }
}
