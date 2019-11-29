package com.pv.persistence.util;

import javax.annotation.Nullable;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static java.util.Optional.ofNullable;
import static org.hibernate.internal.util.collections.CollectionHelper.isEmpty;

public interface JpaUtils {
    @NotNull
    static <T> List<T> getResultListByPageable(@NotNull EntityManager manager, @NotNull CriteriaQuery<T> criteria, @PositiveOrZero int offset, @Positive int size) {
        TypedQuery<T> query = manager.createQuery(criteria);

        query.setFirstResult(offset).setMaxResults(size);

        return query.getResultList();
    }

    @NotNull
    static <T> List<T> getResultList(@NotNull EntityManager manager, @NotNull CriteriaQuery<T> criteria) {
        return manager.createQuery(criteria).getResultList();
    }

    @NotNull
    static <T> T getSingleResult(@NotNull EntityManager manager, @NotNull CriteriaQuery<T> criteria) {
        return manager.createQuery(criteria).getSingleResult();
    }

    static void setPredicate(@NotNull AbstractQuery<?> criteria, @Nullable Collection<@NotNull Predicate> predicates) {
        if (!isEmpty(predicates)) {
            criteria.where(predicates.toArray(new Predicate[0]));
        }
    }

    @NotNull
    static Collection<@NotNull Predicate> between(@NotNull CriteriaBuilder builder, @NotNull Path<LocalDateTime> path, @Nullable LocalDateTime begin, @Nullable LocalDateTime end) {
        Collection<Predicate> predicates = new LinkedList<>();
        ofNullable(begin).map(t -> builder.greaterThanOrEqualTo(path, t)).ifPresent(predicates::add);
        ofNullable(end).map(t -> builder.lessThanOrEqualTo(path, t)).ifPresent(predicates::add);
        return predicates;
    }
}
