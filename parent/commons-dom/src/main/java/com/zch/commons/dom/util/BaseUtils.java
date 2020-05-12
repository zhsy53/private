package com.zch.commons.dom.util;

import javax.annotation.Nullable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.function.Consumer;

public interface BaseUtils {
    @NotNull
    static String trimBeginAndEnd(@NotNull String s) {
        return s.replaceAll("^\\s|\\s$", "");
    }

    @NotNull
    static String joinTogether(@NotNull List<@NotBlank String> list) {
        return String.join("", list);
    }

    static <T> void reverseForeach(@NotNull List<@NotNull T> list, @NotNull Consumer<T> consumer) {
        for (int i = list.size() - 1; i >= 0; i--) {
            consumer.accept(list.get(i));
        }
    }

    @Nullable
    static <T> T getLast(@NotNull List<@NotNull T> list) {
        int size = list.size();
        return size == 0 ? null : list.get(size - 1);
    }
}
