package com.zch.commons.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static java.util.Optional.ofNullable;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class OrderData {
    public static final String DEFAULT_FIELD = "id";

    @NotBlank
    private String field;
    @NotNull
    private boolean desc = true;

    @NotNull
    public static OrderData from(@Nullable OrderRo ro) {
        return from(ofNullable(ro).map(OrderRo::getSortField).orElse(null), ofNullable(ro).map(OrderRo::getDesc).orElse(null));
    }

    @NotNull
    public static OrderData from(@Nullable String field, @Nullable Boolean desc) {
        return from((field), desc, DEFAULT_FIELD);
    }

    @NotNull
    public static OrderData from(@Nullable String field, @Nullable Boolean desc, @NotBlank String defaultField) {
        var ro = new OrderData();
        ro.setField(ofNullable(field).orElse(defaultField));
        ofNullable(desc).ifPresent(ro::setDesc);
        return ro;
    }
}
