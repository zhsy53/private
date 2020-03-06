package com.zch.commons.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collection;
import java.util.List;

@Data(staticConstructor = "of")
public class PageData<T> {
    @PositiveOrZero
    private final int page;
    @Positive
    private final int size;
    @PositiveOrZero
    private final long count;
    @NotNull
    private final Collection<T> content;

    @NotNull
    public static <T> PageData<T> empty(@PositiveOrZero int page, @Positive int size, @PositiveOrZero long count) {
        return PageData.of(page, size, count, List.of());
    }
}
