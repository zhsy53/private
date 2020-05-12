package com.zch.commons.util;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toUnmodifiableList;

public interface FutureUtils {
    static <T> CompletableFuture<List<T>> sequence(@NotNull Stream<CompletableFuture<T>> futures) {
        return sequence(futures.filter(Objects::nonNull).collect(toList()));
    }

    static <T> CompletableFuture<List<T>> sequence(@NotNull List<CompletableFuture<T>> futures) {
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(v -> futures.stream().map(CompletableFuture::join)
                        .collect(toList()));
    }

    static <T> CompletableFuture<T> toCompletable(@NotNull Future<T> future, @Nullable Executor executor) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return future.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }, makeExecutor(executor));
    }

    @NotNull
    private static Executor makeExecutor(@Nullable Executor executor) {
        return Optional.ofNullable(executor).orElse(ForkJoinPool.commonPool());
    }

    static @NotNull <T, R> List<R> executeAll(@NotNull Collection<T> collection, @NotNull Function<T, R> function, @Nullable Executor executor) {
        return executeAll(collection.stream(), function, makeExecutor(executor));
    }

    static @NotNull <T, R> List<R> executeAll(@NotNull Stream<T> stream, @NotNull Function<T, R> function, @Nullable Executor executor) {
        return stream.map(t -> CompletableFuture.supplyAsync(() -> function.apply(t), makeExecutor(executor)))
                .parallel()
                .map(CompletableFuture::join)
                .collect(toUnmodifiableList());
    }

    static @NotNull <T, R> List<R> executeAll(@NotNull Collection<T> collection, @NotNull Function<T, R> function) {
        return executeAll(collection.stream(), function);
    }

    static @NotNull <T, R> List<R> executeAll(@NotNull Stream<T> stream, @NotNull Function<T, R> function) {
        return stream.map(t -> CompletableFuture.supplyAsync(() -> function.apply(t)))
                .parallel()
                .map(CompletableFuture::join)
                .collect(toUnmodifiableList());
    }
}
