package com.zch.commons.domain;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.domain.Page;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

@Data(staticConstructor = "of")
public class PageData<@NotNull T> {
    @PositiveOrZero
    private final int page;
    @Positive
    private final int size;
    @PositiveOrZero
    private final long count;
    @NotNull
    private final Collection<@NotNull T> content;

    @NotNull
    public static <@NotNull T> PageData<@NotNull T> empty(@PositiveOrZero int page, @Positive int size, @PositiveOrZero long count) {
        return PageData.of(page, size, count, List.of());
    }

    @NotNull
    public static <@NotNull T> PageData<@NotNull T> from(@NotNull Page<@NotNull T> page) {
        return of(page.getNumber(), page.getSize(), page.getTotalElements(), page.getContent());
    }

    @NotNull <@NotNull R> PageData<@NonNull R> map(@NotNull Function<? super T, ? extends R> converter) {
        return of(page, size, count, content.stream().map(converter).collect(toList()));
    }
}
