package com.zch.commons.domain;

import lombok.Data;

import javax.annotation.Nullable;

@Data
public class OrderRo {
    @Nullable
    private String sortField;
    @Nullable
    private Boolean desc;
}
