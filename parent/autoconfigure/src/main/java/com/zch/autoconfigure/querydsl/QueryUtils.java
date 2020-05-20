package com.zch.autoconfigure.querydsl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BeanPath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.zch.commons.domain.OrderData;
import com.zch.commons.domain.OrderRo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.ReflectionUtils;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.zch.commons.util.LambdaUtils.rethrowFunction;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

public interface QueryUtils {
    @NotNull
    static <T> Page<T> page(@NotNull QueryResults<T> results, @NotNull Pageable pageable) {
        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    @NotNull
    static Predicate[] predicates(@NotNull List<Predicate> predicates) {
        return predicates.toArray(new Predicate[0]);
    }

    @NotNull
    static OrderSpecifier<?> order(@NotNull EntityPathBase<?> entityPath, @NotNull OrderRo ro) {
        return order(entityPath, OrderData.from(ro));
    }

    @NotNull
    static OrderSpecifier<?> order(@NotNull EntityPathBase<?> entityPath, @NotNull OrderData ro) {
        return order(entityPath, ro.getField(), ro.isDesc());
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @NotNull
    static OrderSpecifier<?> order(@NotNull EntityPathBase<?> entityPath, @Nullable String field, @Nullable Boolean desc) {
        var path = listPathFromEntityPath(entityPath).stream().filter(p -> p.getMetadata().getName().equals(ofNullable(field).orElse(OrderData.DEFAULT_FIELD))).findAny().orElseThrow();
        return new OrderSpecifier(ofNullable(desc).orElse(false) ? Order.DESC : Order.ASC, path);
    }

    @SuppressWarnings("unchecked")
    @NotNull
    private static List<Path<?>> listPathFromEntityPath(@NotNull EntityPathBase<?> entityPath) {
        try {
            return listFieldFromEntityPath((Class<EntityPathBase<?>>) entityPath.getClass())
                    .stream()
                    .map(rethrowFunction(f -> f.get(entityPath)))
                    .filter(Objects::nonNull)
                    .map(p -> (Path<?>) p)
                    .collect(toList());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    private static List<@NotNull Field> listFieldFromEntityPath(@NotNull Class<? extends EntityPathBase<?>> clazz) {
        var fields = new ArrayList<Field>();

        ReflectionUtils.doWithFields(clazz, field -> {
            var type = field.getType();
            var modifiers = field.getModifiers();

            if (
                    Path.class.isAssignableFrom(type) && !BeanPath.class.isAssignableFrom(type)
                            && field.getDeclaringClass() != BeanPath.class
                            && Modifier.isPublic(modifiers) && Modifier.isPublic(modifiers)
            ) {
                fields.add(field);
            }
        });

        return fields;
    }
}
